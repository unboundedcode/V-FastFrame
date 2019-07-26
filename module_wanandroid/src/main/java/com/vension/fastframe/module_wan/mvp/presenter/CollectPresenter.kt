package com.vension.fastframe.module_wan.mvp.presenter

import com.vension.fastframe.module_wan.api.ApiService
import com.vension.fastframe.module_wan.bean.CollectionArticle
import com.vension.fastframe.module_wan.bean.CollectionResponseBody
import com.vension.fastframe.module_wan.bean.HttpResult
import com.vension.fastframe.module_wan.mvp.contract.CollectContract
import kv.vension.vframe.core.mvp.AbsPresenter
import kv.vension.vframe.net.RetrofitHelper
import kv.vension.vframe.net.exception.ExceptionHandle
import kv.vension.vframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/23 11:08
 * 描  述：
 * ========================================================
 */

class CollectPresenter : AbsPresenter<CollectContract.View>(), CollectContract.Presenter {

    override fun getCollectList(page: Int) {
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID, ApiService::class.java)
                .getCollectList(page)
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<HttpResult<CollectionResponseBody<CollectionArticle>>>())
                .subscribe(
                    {
                        run {
                            mView?.setCollectList(page,it.data)
                        }
                    },
                    {t->
                        mView?.dismissProgressDialog()
                        mView?.let {
                            ExceptionHandle.handleException(t, it)
                        }
                    }
                )
        )
    }

    override fun removeCollectArticle(id: Int, originId: Int) {
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID, ApiService::class.java)
                .removeCollectArticle(id,originId)
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<HttpResult<Any>>())
                .subscribe(
                    {
                        run {
                            mView?.showRemoveCollectSuccess(true)
                        }
                    },
                    {t->
                        mView?.dismissProgressDialog()
                        mView?.let {
                            ExceptionHandle.handleException(t, it)
                        }
                    }
                )
        )
    }

}