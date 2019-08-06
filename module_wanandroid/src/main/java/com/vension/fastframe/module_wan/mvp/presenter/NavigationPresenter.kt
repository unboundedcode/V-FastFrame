package com.vension.fastframe.module_wan.mvp.presenter

import com.vension.fastframe.module_wan.api.ApiService
import com.vension.fastframe.module_wan.bean.HttpResult
import com.vension.fastframe.module_wan.bean.NavigationBean
import com.vension.fastframe.module_wan.mvp.contract.NavigationContract
import kv.vension.fastframe.core.mvp.AbsPresenter
import kv.vension.fastframe.net.RetrofitHelper
import kv.vension.fastframe.net.exception.ExceptionHandle
import kv.vension.fastframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/22 16:46
 * 描  述：
 * ========================================================
 */

class NavigationPresenter : AbsPresenter<NavigationContract.View>(), NavigationContract.Presenter {

    override fun requestNavigationList() {
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID, ApiService::class.java)
                .getNavigationList()
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<HttpResult<List<NavigationBean>>>())
                .subscribe(
                    {
                        run {
                            mView?.setNavigationData(it.data)
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