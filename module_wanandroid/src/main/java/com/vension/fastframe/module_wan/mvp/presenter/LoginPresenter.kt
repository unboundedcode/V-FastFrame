package com.vension.fastframe.module_wan.mvp.presenter

import com.vension.fastframe.module_wan.api.ApiService
import com.vension.fastframe.module_wan.bean.HttpResult
import com.vension.fastframe.module_wan.bean.LoginData
import com.vension.fastframe.module_wan.mvp.contract.LoginContract
import kv.vension.vframe.core.mvp.AbsPresenter
import kv.vension.vframe.net.RetrofitHelper
import kv.vension.vframe.net.exception.ExceptionHandle
import kv.vension.vframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/21 15:33.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
class LoginPresenter : AbsPresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun loginWanAndroid(username: String, password: String) {
        mView?.showProgressDialog()
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID,ApiService::class.java)
                .loginWanAndroid(username, password)
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<HttpResult<LoginData>>())
                .subscribe(
                    {
                        run {
                            mView?.loginSuccess(it.data)
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