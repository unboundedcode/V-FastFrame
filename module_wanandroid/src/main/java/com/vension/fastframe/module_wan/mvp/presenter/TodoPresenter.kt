package com.vension.fastframe.module_wan.mvp.presenter

import com.vension.fastframe.module_wan.api.ApiService
import com.vension.fastframe.module_wan.bean.AllTodoResponseBody
import com.vension.fastframe.module_wan.bean.HttpResult
import com.vension.fastframe.module_wan.bean.TodoResponseBody
import com.vension.fastframe.module_wan.mvp.contract.TodoContract
import kv.vension.fastframe.core.mvp.AbsPresenter
import kv.vension.fastframe.net.RetrofitHelper
import kv.vension.fastframe.net.exception.ExceptionHandle
import kv.vension.fastframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig


/**
 * Created by chenxz on 2018/8/7.
 */
class TodoPresenter : AbsPresenter<TodoContract.View>(), TodoContract.Presenter {

    override fun getAllTodoList(type: Int) {
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID, ApiService::class.java)
                .getTodoList(type)
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<HttpResult<AllTodoResponseBody>>())
                .subscribe(
                    {
                        run {
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

    override fun getNoTodoList(page: Int, type: Int) {
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID, ApiService::class.java)
                .getNoTodoList(page,type)
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<HttpResult<TodoResponseBody>>())
                .subscribe(
                    {
                        run {
                            mView?.showNoTodoList(it.data)
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

    override fun getDoneList(page: Int, type: Int) {
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID, ApiService::class.java)
                .getDoneList(page,type)
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<HttpResult<TodoResponseBody>>())
                .subscribe(
                    {
                        run {
                            mView?.showNoTodoList(it.data)
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

    override fun deleteTodoById(id: Int) {
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID, ApiService::class.java)
                .deleteTodoById(id)
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<HttpResult<Any>>())
                .subscribe(
                    {
                        run {
                            mView?.showDeleteSuccess(true)
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

    override fun updateTodoById(id: Int, status: Int) {
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID, ApiService::class.java)
                .updateTodoById(id,status)
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<HttpResult<Any>>())
                .subscribe(
                    {
                        run {
                            mView?.showUpdateSuccess(true)
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