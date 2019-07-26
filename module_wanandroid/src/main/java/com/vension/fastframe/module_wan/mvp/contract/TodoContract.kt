package com.vension.fastframe.module_wan.mvp.contract

import com.vension.fastframe.module_wan.bean.AllTodoResponseBody
import com.vension.fastframe.module_wan.bean.HttpResult
import com.vension.fastframe.module_wan.bean.TodoResponseBody
import kv.vension.vframe.core.mvp.IPresenter
import kv.vension.vframe.core.mvp.IView
import io.reactivex.Observable

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/26 17:05.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
interface TodoContract {

    interface View : IView {

        fun showNoTodoList(todoResponseBody: TodoResponseBody)

        fun showDeleteSuccess(success: Boolean)

        fun showUpdateSuccess(success: Boolean)

    }

    interface Presenter : IPresenter<View> {

        fun getAllTodoList(type: Int)

        fun getNoTodoList(page: Int, type: Int)

        fun getDoneList(page: Int, type: Int)

        fun deleteTodoById(id: Int)

        fun updateTodoById(id: Int, status: Int)

    }

}