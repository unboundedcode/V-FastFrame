package com.vension.fastframe.module_wan.mvp.contract

import com.vension.fastframe.module_wan.bean.HttpResult
import kv.vension.vframe.core.mvp.IPresenter
import kv.vension.vframe.core.mvp.IView
import io.reactivex.Observable

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/26 12:15.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
interface TodoAddContract {

    interface View : IView {

        fun showAddTodo(success: Boolean)

        fun showUpdateTodo(success: Boolean)

        fun getType(): Int
        fun getCurrentDate(): String
        fun getTitle(): String
        fun getContent(): String
        fun getStatus(): Int
        fun getItemId(): Int
        fun getPriority(): String
    }

    interface Presenter : IPresenter<View> {

        fun addTodo()

        fun updateTodo(id: Int)

    }

}