package com.vension.fastframe.module_wan.mvp.presenter

import com.vension.fastframe.module_wan.api.ApiService
import com.vension.fastframe.module_wan.bean.HttpResult
import com.vension.fastframe.module_wan.bean.LoginData
import com.vension.fastframe.module_wan.mvp.contract.RegisterContract
import kv.vension.fastframe.core.mvp.AbsPresenter
import kv.vension.fastframe.net.RetrofitHelper
import kv.vension.fastframe.net.exception.ExceptionHandle
import kv.vension.fastframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/21 15:55
 * 描  述：
 * ========================================================
 */

class RegisterPresenter : AbsPresenter<RegisterContract.View>(), RegisterContract.Presenter {

    override fun registerWanAndroid(username: String, password: String, repassword: String) {
        mView?.showProgressDialog()
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID,ApiService::class.java)
                .registerWanAndroid(username, password, repassword)
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<HttpResult<LoginData>>())
                .subscribe(
                    {
                        run {
                            mView?.registerSuccess(it.data)
                        }
                    },
                    {t->
                        mView?.dismissProgressDialog()
                        mView?.registerFail()
                        mView?.let {
                            ExceptionHandle.handleException(t, it)
                        }
                    }
                )
        )
    }

}