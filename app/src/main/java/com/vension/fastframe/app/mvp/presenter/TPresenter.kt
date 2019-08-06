package com.vension.fastframe.app.mvp.presenter

import com.vension.fastframe.app.api.ApiService
import com.vension.fastframe.app.bean.TBean
import com.vension.fastframe.app.mvp.contract.TContract
import kv.vension.fastframe.core.mvp.AbsPresenter
import kv.vension.fastframe.net.RetrofitHelper
import kv.vension.fastframe.net.response.BaseResponseBean
import kv.vension.fastframe.net.rx.FlowableSubscriberManager
import kv.vension.fastframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig

/**
 *创建人:雷富
 *创建时间:2019/6/6 13:57
 *描述:
 */
class TPresenter : AbsPresenter<TContract.View>(), TContract.Presenter {


    override fun getObjectData() {
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL, ApiService::class.java)
                .getTData()
                .compose(RxHandler.rxSchedulerFlowableToMain<BaseResponseBean<TBean>>())//需要加上这句转化
                .compose(RxHandler.handleFlowableResult2())//通过泛型处理
                .subscribeWith(
                    object : FlowableSubscriberManager<TBean>(mView, true) {
                        override fun onNext(t: TBean) {
                            this@TPresenter.mView?.showObjectData(t)
                        }
                    })
        )
    }
}