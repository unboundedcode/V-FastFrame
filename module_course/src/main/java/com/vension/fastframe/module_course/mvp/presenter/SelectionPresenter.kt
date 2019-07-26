package com.vension.fastframe.module_course.mvp.presenter

import com.vension.fastframe.module_course.RxHandlerWK
import com.vension.fastframe.module_course.api.ApiService
import com.vension.fastframe.module_course.bean.HttpResult
import com.vension.fastframe.module_course.bean.SelectionBean
import com.vension.fastframe.module_course.mvp.contract.SelectionContract
import kv.vension.vframe.core.mvp.AbsPresenter
import kv.vension.vframe.net.RetrofitHelper
import kv.vension.vframe.net.exception.ExceptionHandle
import kv.vension.vframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/26 09:35.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/26 09:35
 * @desc:
 * ===================================================================
 */
class SelectionPresenter : AbsPresenter<SelectionContract.View>(),SelectionContract.Presenter {

    override fun getSelection() {
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WEIKE, ApiService::class.java)
                .getSelection()
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandlerWK.handleObservableWKResult<HttpResult<SelectionBean>>())
                .subscribe(
                    {
                        run {
                            mView?.showSelection(it.data)
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