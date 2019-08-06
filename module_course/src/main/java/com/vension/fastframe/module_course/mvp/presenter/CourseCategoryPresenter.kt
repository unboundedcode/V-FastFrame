package com.vension.fastframe.module_course.mvp.presenter

import com.vension.fastframe.module_course.RxHandlerWK
import com.vension.fastframe.module_course.api.ApiService
import com.vension.fastframe.module_course.bean.HttpResult
import com.vension.fastframe.module_course.bean.VideoGroup
import com.vension.fastframe.module_course.mvp.contract.CourseCategoryContract
import kv.vension.fastframe.core.mvp.AbsPresenter
import kv.vension.fastframe.net.RetrofitHelper
import kv.vension.fastframe.net.exception.ExceptionHandle
import kv.vension.fastframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/25 15:33.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/25 15:33
 * @desc:
 * ===================================================================
 */
class CourseCategoryPresenter : AbsPresenter<CourseCategoryContract.View>(),CourseCategoryContract.Presenter {

    override fun getVideo() {
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WEIKE, ApiService::class.java)
                .getVideo()
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandlerWK.handleObservableWKResult<HttpResult<VideoGroup>>())
                .subscribe(
                    {
                        run {
                            mView?.showVideo(it.data)
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