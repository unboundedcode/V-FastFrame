package com.vension.fastframe.module_wan.mvp.contract

import kv.vension.fastframe.core.mvp.IPresenter
import kv.vension.fastframe.core.mvp.IView

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/27 11:43
 * 描  述：
 * ========================================================
 */

interface MainContract {

    interface View : IView {
        fun showLogoutSuccess(success: Boolean)
    }

    interface Presenter : IPresenter<View> {

        fun logout()

    }

}