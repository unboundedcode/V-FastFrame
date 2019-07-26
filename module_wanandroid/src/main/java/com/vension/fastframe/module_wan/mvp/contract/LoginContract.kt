package com.vension.fastframe.module_wan.mvp.contract

import com.vension.fastframe.module_wan.bean.HttpResult
import com.vension.fastframe.module_wan.bean.LoginData
import io.reactivex.Observable
import kv.vension.vframe.core.mvp.IPresenter
import kv.vension.vframe.core.mvp.IView

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/21 15:29.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
interface LoginContract {

    interface View : IView {
        fun loginSuccess(data: LoginData)
        fun loginFail()
    }


    interface Presenter : IPresenter<View> {
        fun loginWanAndroid(username: String, password: String)
    }

}