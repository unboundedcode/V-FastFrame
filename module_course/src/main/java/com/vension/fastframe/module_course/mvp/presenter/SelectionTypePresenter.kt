package com.vension.fastframe.module_course.mvp.presenter

import com.vension.fastframe.module_course.RxHandlerWK
import com.vension.fastframe.module_course.api.ApiService
import com.vension.fastframe.module_course.bean.HttpResult
import com.vension.fastframe.module_course.bean.VerticalBean
import com.vension.fastframe.module_course.mvp.contract.SelectionTypeContract
import kv.vension.fastframe.core.mvp.AbsPresenter
import kv.vension.fastframe.net.RetrofitHelper
import kv.vension.fastframe.net.exception.ExceptionHandle
import kv.vension.fastframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/26 12:02.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/26 12:02
 * @desc:
 * ===================================================================
 */
class SelectionTypePresenter : AbsPresenter<SelectionTypeContract.View>(),SelectionTypeContract.Presenter{
    override fun getVertical(tag: Int) {
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WEIKE, ApiService::class.java)
                .getVertical(tag)
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandlerWK.handleObservableWKResult<HttpResult<VerticalBean>>())
                .subscribe(
                    {
                        run {
                            mView?.showVertical(it.data)
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