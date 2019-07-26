package com.vension.fastframe.module_wan.mvp.presenter

import com.vension.fastframe.module_wan.api.ApiService
import com.vension.fastframe.module_wan.bean.HttpResult
import com.vension.fastframe.module_wan.bean.KnowledgeTreeBody
import com.vension.fastframe.module_wan.mvp.contract.KnowledgeTreeContract
import kv.vension.vframe.core.mvp.AbsPresenter
import kv.vension.vframe.net.RetrofitHelper
import kv.vension.vframe.net.exception.ExceptionHandle
import kv.vension.vframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/7 15:53.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
class KnowledgeTreePresenter : AbsPresenter<KnowledgeTreeContract.View>(),
    KnowledgeTreeContract.Presenter{

    override fun requestKnowledgeTree() {
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID, ApiService::class.java)
                .getKnowledgeTree()
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<HttpResult<List<KnowledgeTreeBody>>>())
                .subscribe(
                    {
                        run {
                            mView?.setRefreshData(it.data as MutableList<KnowledgeTreeBody>)
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