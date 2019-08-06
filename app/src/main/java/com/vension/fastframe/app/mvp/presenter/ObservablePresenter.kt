package com.vension.fastframe.app.mvp.presenter

import com.vension.fastframe.app.api.ApiService
import com.vension.fastframe.app.bean.ObjectBean
import com.vension.fastframe.app.mvp.contract.ObservableContract
import kv.vension.fastframe.core.mvp.AbsPresenter
import kv.vension.fastframe.net.RetrofitHelper
import kv.vension.fastframe.net.exception.ExceptionHandle
import kv.vension.fastframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig

/**
 *创建人:雷富
 *创建时间:2019/6/6 13:57
 *描述:
 */
class ObservablePresenter : AbsPresenter<ObservableContract.View>(), ObservableContract.Presenter {


    override fun getObjectData() {
        mView?.showProgressDialog()
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL,ApiService::class.java)
            .getObservableObjectData()
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<ObjectBean>())
                .subscribe({
                    run { ->
                        mView?.showObjectData(it)
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