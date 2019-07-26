package com.vension.fastframe.module_wan.mvp.presenter

import com.vension.fastframe.module_wan.api.ApiService
import com.vension.fastframe.module_wan.mvp.contract.CommonContract
import kv.vension.vframe.core.mvp.AbsPresenter
import kv.vension.vframe.net.RetrofitHelper
import kv.vension.vframe.net.exception.ExceptionHandle
import kv.vension.vframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/6 14:39
 * 描  述：
 * ========================================================
 */

open class CommonPresenter<data,V : CommonContract.View<data>>
    : AbsPresenter<V>(), CommonContract.Presenter<data,V> {


    override fun addCollectArticle(id: Int) {
        mView?.showProgressDialog()
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID, ApiService::class.java)
                .addCollectArticle(id)
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<Any>())
                .subscribe({
                    run { ->
                        mView?.showCollectSuccess(success = true)
                        mView?.dismissProgressDialog()
                    }
                },
                    { t ->
                        mView?.dismissProgressDialog()
                        mView?.let { ExceptionHandle.handleException(t, it) }
                    })
        )
    }

    override fun cancelCollectArticle(id: Int) {
        mView?.showProgressDialog()
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID, ApiService::class.java)
                .cancelCollectArticle(id)
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<Any>())
                .subscribe({
                    run { ->
                        mView?.showCancelCollectSuccess(success = true)
                        mView?.dismissProgressDialog()
                    }
                },
                    { t ->
                        mView?.dismissProgressDialog()
                        mView?.let { ExceptionHandle.handleException(t, it) }
                    })
        )
    }

}