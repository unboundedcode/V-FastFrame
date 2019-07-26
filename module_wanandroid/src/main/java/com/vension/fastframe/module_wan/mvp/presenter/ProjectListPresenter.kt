package com.vension.fastframe.module_wan.mvp.presenter

import com.vension.fastframe.module_wan.api.ApiService
import com.vension.fastframe.module_wan.bean.Article
import com.vension.fastframe.module_wan.bean.ArticleResponseBody
import com.vension.fastframe.module_wan.bean.HttpResult
import com.vension.fastframe.module_wan.mvp.contract.ProjectListContract
import kv.vension.vframe.net.RetrofitHelper
import kv.vension.vframe.net.exception.ExceptionHandle
import kv.vension.vframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/22 15:43.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
class ProjectListPresenter : CommonPresenter<Article, ProjectListContract.View<Article>>(),
    ProjectListContract.Presenter<Article>{

    override fun requestProjectList(page: Int, cid: Int) {
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID, ApiService::class.java)
                .getProjectList(page,cid)
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<HttpResult<ArticleResponseBody>>())
                .subscribe(
                    {
                        run {
                            mView?.setProjectList(page, it.data)
                        }
                    },
                    {t->
                        mView?.let {
                            ExceptionHandle.handleException(t, it)
                        }
                    }
                )
        )
    }

}