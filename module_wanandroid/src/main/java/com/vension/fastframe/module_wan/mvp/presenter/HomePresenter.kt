package com.vension.fastframe.module_wan.mvp.presenter

import android.annotation.SuppressLint
import com.vension.fastframe.module_wan.api.ApiService
import com.vension.fastframe.module_wan.bean.Article
import com.vension.fastframe.module_wan.bean.ArticleResponseBody
import com.vension.fastframe.module_wan.bean.Banner
import com.vension.fastframe.module_wan.bean.HttpResult
import com.vension.fastframe.module_wan.mvp.contract.HomeContract
import com.vension.fastframe.module_wan.util.SettingUtil
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kv.vension.vframe.net.RetrofitHelper
import kv.vension.vframe.net.exception.ExceptionHandle
import kv.vension.vframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/6 14:38.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
class HomePresenter : CommonPresenter<Article, HomeContract.View<Article>>(), HomeContract.Presenter<Article>{

    @SuppressLint("CheckResult")
    override fun requestHomeData() {
        requestBanner()
        val observable = if (SettingUtil.getIsShowTopArticle()) {
            getArticles(0)
        } else {
            Observable.zip(getTopArticles(), getArticles(0),
                BiFunction<HttpResult<MutableList<Article>>, HttpResult<ArticleResponseBody>,
                        HttpResult<ArticleResponseBody>> { t1, t2 ->
                    t1.data.forEach {
                        // 置顶数据中没有标识，手动添加一个标识
                        it.top = "1"
                    }
                    t2.data.datas.addAll(0, t1.data)
                    t2
                })
        }
        observable.compose(RxHandler.handleObservableResult<HttpResult<ArticleResponseBody>>())
            .subscribe(
                {
                    run {
                        mView?.setArticles(it.data)
                    }
                },
                {t->
                    mView?.let { ExceptionHandle.handleException(t, it) }
                }
            )
    }



    override fun requestBanner() {
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID, ApiService::class.java)
                .getBanners()
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<HttpResult<List<Banner>>>())
                .subscribe(
                    {
                        run {
                            mView?.setBanners(it.data)
                        }
                    },
                    {t->
                        mView?.let { ExceptionHandle.handleException(t, it) }
                    }
                )
        )
    }

    override fun requestArticles(num: Int) {
        addSubscription(
            getArticles(num)
                .compose(RxHandler.handleObservableResult<HttpResult<ArticleResponseBody>>())
                .subscribe(
                    {
                        run {
                            mView?.setArticles(it.data)
                        }
                    },
                    {t->
                        mView?.let { ExceptionHandle.handleException(t, it) }
                    }
                )
        )
    }

    private fun getArticles(num:Int):Observable<HttpResult<ArticleResponseBody>> {
        return RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID, ApiService::class.java)
            .getArticles(num)
            .compose(RxHandler.rxSchedulerObservableToMain())
    }


    private fun getTopArticles(): Observable<HttpResult<MutableList<Article>>> {
        return RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID, ApiService::class.java)
            .getTopArticles()
            .compose(RxHandler.rxSchedulerObservableToMain())
    }

}