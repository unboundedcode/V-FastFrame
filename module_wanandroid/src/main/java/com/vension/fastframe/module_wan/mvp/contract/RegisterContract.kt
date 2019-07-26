package com.vension.fastframe.module_wan.mvp.contract

import com.vension.fastframe.module_wan.bean.LoginData
import kv.vension.vframe.core.mvp.IPresenter
import kv.vension.vframe.core.mvp.IView


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/21 15:50
 * 描  述：
 * ========================================================
 */

interface RegisterContract {

    interface View : IView {

        fun registerSuccess(data: LoginData)

        fun registerFail()
    }

    interface Presenter : IPresenter<View> {

        fun registerWanAndroid(username: String, password: String, repassword: String)

    }

}