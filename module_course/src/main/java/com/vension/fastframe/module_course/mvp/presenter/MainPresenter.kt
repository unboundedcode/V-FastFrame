package com.vension.fastframe.module_course.mvp.presenter

import com.vension.fastframe.module_course.RxHandlerWK
import com.vension.fastframe.module_course.api.ApiService
import com.vension.fastframe.module_course.bean.HttpResult
import com.vension.fastframe.module_course.mvp.contract.MainContract
import kv.vension.vframe.core.mvp.AbsPresenter
import kv.vension.vframe.net.RetrofitHelper
import kv.vension.vframe.net.exception.ExceptionHandle
import kv.vension.vframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig
import java.util.*


/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/26 11:08
 * 更 新：2019/7/26 11:08
 * 描 述：主界面-MainPresenter
 * ========================================================
 */

class MainPresenter : AbsPresenter<MainContract.View>(), MainContract.Presenter {

    override fun getRegionTagTypeBean(tagId: ArrayList<Int>) {
        mView?.showProgressDialog()
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WEIKE, ApiService::class.java)
                .getDiscoveryComment()
                .flatMap {
                    mView?.showDiscoveryBean(it.data)
                    val map = HashMap<String, Any>()
                    map["tags"] = tagId
                    RetrofitHelper.getService(HttpConfig.BASE_URL_WEIKE, ApiService::class.java).setTag(map)
                }
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandlerWK.handleObservableWKResult<HttpResult<Any>>())
                .subscribe(
                    {
                        run {
                            mView?.dismissProgressDialog()
                            mView?.showSetTag()
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
