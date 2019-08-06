package com.vension.fastframe.app.mvp.presenter

import com.vension.fastframe.app.api.ApiService
import com.vension.fastframe.app.bean.ObjectBean
import com.vension.fastframe.app.mvp.contract.FlowableContract
import kv.vension.fastframe.core.mvp.AbsPresenter
import kv.vension.fastframe.net.RetrofitHelper
import kv.vension.fastframe.net.exception.ExceptionHandle
import kv.vension.fastframe.net.rx.FlowableSubscriberManager
import kv.vension.fastframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig

/**
 *创建人:雷富
 *创建时间:2019/6/6 13:57
 *描述:
 */
class FlowablePresenter : AbsPresenter<FlowableContract.View>(), FlowableContract.Presenter {


    override fun getObjectData() {
//        mView?.showLoading() 不用处理加载中和取消加载,通过true false控制
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL, ApiService::class.java)
                .getFlowableObjectData()
                .compose(RxHandler.rxSchedulerFlowableToMain())
                .compose(RxHandler.handleFlowableResult<ObjectBean>())
                .subscribeWith(
                    object : FlowableSubscriberManager<ObjectBean>(mView, true) {
                        override fun onNext(t: ObjectBean) {
                            this@FlowablePresenter.mView?.showObjectData(t)
                        }

                        override fun onError(e: Throwable) {
                            super.onError(e)
                            mView?.dismissProgressDialog()
                            mView?.let { ExceptionHandle.handleException(e, it) }
                        }
                    })
        )
    }
}