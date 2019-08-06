package lib.vension.fastframe.common.mvp

import android.os.Handler
import kv.vension.fastframe.core.mvp.AbsPresenter

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/29 10:29.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
class LoginPresenter : AbsPresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun doLogin() {
        mView?.showProgressDialog()
        Handler().postDelayed({
            mView?.dismissProgressDialog()
            mView?.loginSuccess("Login Success" + "\n" + "I am Vension")
        }, 2000)
    }

}