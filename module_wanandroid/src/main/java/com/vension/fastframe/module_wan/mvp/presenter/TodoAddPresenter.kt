package com.vension.fastframe.module_wan.mvp.presenter

import com.vension.fastframe.module_wan.api.ApiService
import com.vension.fastframe.module_wan.bean.HttpResult
import com.vension.fastframe.module_wan.mvp.contract.TodoAddContract
import kv.vension.fastframe.core.mvp.AbsPresenter
import kv.vension.fastframe.net.RetrofitHelper
import kv.vension.fastframe.net.exception.ExceptionHandle
import kv.vension.fastframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/26 17:56
 * 描  述：
 * ========================================================
 */

class TodoAddPresenter : AbsPresenter<TodoAddContract.View>(), TodoAddContract.Presenter {

    override fun addTodo() {
        val type = mView?.getType() ?: 0
        val title = mView?.getTitle().toString()
        val content = mView?.getContent().toString()
        val date = mView?.getCurrentDate().toString()
        val priority = mView?.getPriority().toString()

        val map = mutableMapOf<String, Any>()
        map["type"] = type
        map["title"] = title
        map["content"] = content
        map["date"] = date
        map["priority"] = priority

        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID, ApiService::class.java)
                .addTodo(map)
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<HttpResult<Any>>())
                .subscribe(
                    {
                        run {
                            mView?.showAddTodo(true)
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

    override fun updateTodo(id: Int) {
        val type = mView?.getType() ?: 0
        val title = mView?.getTitle().toString()
        val content = mView?.getContent().toString()
        val date = mView?.getCurrentDate().toString()
        val status = mView?.getStatus() ?: 0
        val priority = mView?.getPriority().toString()

        val map = mutableMapOf<String, Any>()
        map["type"] = type
        map["title"] = title
        map["content"] = content
        map["date"] = date
        map["status"] = status
        map["priority"] = priority

        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID, ApiService::class.java)
                .updateTodo(id,map)
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<HttpResult<Any>>())
                .subscribe(
                    {
                        run {
                            mView?.showUpdateTodo(true)
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