package lib.vension.fastframe.common.mvp

import kv.vension.vframe.core.mvp.IPresenter
import kv.vension.vframe.core.mvp.IView

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/18 11:51.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/18 11:51
 * @desc:   character determines attitude, attitude determines destiny
 * ===================================================================
 */
interface LoginContract {

    interface View : IView {
        fun loginSuccess(data: String)
    }

    interface Presenter : IPresenter<View> {
        fun doLogin()
    }


}