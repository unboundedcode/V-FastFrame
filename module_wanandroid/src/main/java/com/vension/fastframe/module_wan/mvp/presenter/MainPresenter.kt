package com.vension.mvpforkotlin.sample.mvp.presenter

import com.vension.fastframe.module_wan.api.ApiService
import com.vension.fastframe.module_wan.mvp.contract.MainContract
import kv.vension.fastframe.core.mvp.AbsPresenter
import kv.vension.fastframe.net.RetrofitHelper
import kv.vension.fastframe.net.exception.ExceptionHandle
import kv.vension.fastframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/27 11:45
 * 描  述：
 * ========================================================
 */

class MainPresenter : AbsPresenter<MainContract.View>(), MainContract.Presenter {


    override fun logout() {
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID, ApiService::class.java)
                .logout()
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<Any>())
                .subscribe(
                    {
                        run { ->
                            mView?.showLogoutSuccess(success = true)
                            mView?.dismissProgressDialog()
                        }
                    }, { t ->
                        mView?.dismissProgressDialog()
                        mView?.let { ExceptionHandle.handleException(t, it) }
                    })
        )
    }

}