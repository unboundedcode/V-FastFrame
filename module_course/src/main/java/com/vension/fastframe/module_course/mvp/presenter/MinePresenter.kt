package com.vension.fastframe.module_course.mvp.presenter

import com.vension.fastframe.module_course.api.ApiService
import com.vension.fastframe.module_course.bean.HttpResult
import com.vension.fastframe.module_course.bean.MineBean
import com.vension.fastframe.module_course.mvp.contract.MineContract
import kv.vension.fastframe.core.mvp.AbsPresenter
import kv.vension.fastframe.net.RetrofitHelper
import kv.vension.fastframe.net.exception.ExceptionHandle
import kv.vension.fastframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/25 11:25.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/25 11:25
 * @desc:   character determines attitude, attitude determines destiny
 * ===================================================================
 */
class MinePresenter : AbsPresenter<MineContract.View>(),MineContract.Presenter {

    override fun getMine() {
        mView?.showProgressDialog()
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WEIKE, ApiService::class.java)
                .getMine()
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<HttpResult<MineBean>>())
                .subscribe(
                    {
                        run {
                            mView?.showMine(it.data)
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