package com.vension.fastframe.module_wan.mvp.presenter

import com.vension.fastframe.module_wan.api.ApiService
import com.vension.fastframe.module_wan.bean.HttpResult
import com.vension.fastframe.module_wan.bean.ProjectTreeBean
import com.vension.fastframe.module_wan.mvp.contract.ProjectContract
import kv.vension.vframe.core.mvp.AbsPresenter
import kv.vension.vframe.net.RetrofitHelper
import kv.vension.vframe.net.exception.ExceptionHandle
import kv.vension.vframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/22 15:14.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
class ProjectPresenter : AbsPresenter<ProjectContract.View>(), ProjectContract.Presenter {


    override fun requestProjectTree() {
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID, ApiService::class.java)
                .getProjectTree()
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<HttpResult<List<ProjectTreeBean>>>())
                .subscribe(
                    {
                        run {
                            mView?.setProjectTree(it.data)
                        }
                    },
                    {t->
                        mView?.let {
                            ExceptionHandle.handleException(t, it)
                        }
                    }
                )
        )
    }

}