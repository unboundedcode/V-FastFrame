package com.vension.fastframe.module_news.mvp.presenter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vension.fastframe.module_news.NewsConstant
import com.vension.fastframe.module_news.RxHandlerNews
import com.vension.fastframe.module_news.api.NewsApiService
import com.vension.fastframe.module_news.bean.FrontNewsModel
import com.vension.fastframe.module_news.bean.NewsMainModel
import com.vension.fastframe.module_news.mvp.contract.HeadContract
import com.vension.fastframe.module_news.utils.FileUtil
import kv.vension.fastframe.VFrame
import kv.vension.fastframe.core.mvp.AbsPresenter
import kv.vension.fastframe.net.RetrofitHelper
import kv.vension.fastframe.net.exception.ExceptionHandle
import kv.vension.fastframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/30 15:00.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/30 15:00
 * @desc:
 * ===================================================================
 */
class HeadPresenter : AbsPresenter<HeadContract.View>(), HeadContract.Presenter {

    override fun getNewChannels() {
        mView?.showLoading()
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_NEWS,NewsApiService::class.java)
                .getHeaderNews("T1348647909107",0,20)
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandlerNews.handleObservableNewsResult<NewsMainModel>())
                .subscribe(
                    {
                        run {
                             mView?.setNewChannels(it)
                        }
                    },
                    { t->
                        mView?.dismissProgressDialog()
                        mView?.let {
                            ExceptionHandle.handleException(t, it)
                        }
                    }
                )
        )
    }


    override fun getFrontNewsData(flag: Int) {
        val multiIndexData = getMultiIndexJsonData(if (flag == 1) NewsConstant.ASSET_FRONT_NEWS else NewsConstant.ASSET_FRONT_NEWS_MORE) as FrontNewsModel
        mView?.setFrontNewsData(multiIndexData)
    }

    override fun getFrontNewsMoreWares() {
        mView?.setFrontNewsMoreWares(getMultiIndexJsonData(NewsConstant.ASSET_FRONT_NEWS_MORE))
    }

    /**
     * 解析json
     */
    fun getMultiIndexJsonData(fileName: String): FrontNewsModel {
        val json = FileUtil.getJson(VFrame.getContext(), fileName)
        val gson = Gson()
        val type = object : TypeToken<FrontNewsModel>() {}.type
        return gson.fromJson<FrontNewsModel>(json, type)
    }

}