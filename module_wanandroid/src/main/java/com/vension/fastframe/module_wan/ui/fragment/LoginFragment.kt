package com.vension.fastframe.module_wan.ui.fragment

import android.os.Bundle
import android.view.View
import com.leifu.mvpkotlin.util.PreferenceUtil
import com.vension.fastframe.module_wan.Constant
import com.vension.fastframe.module_wan.R
import com.vension.fastframe.module_wan.bean.LoginData
import com.vension.fastframe.module_wan.event.LoginEvent
import com.vension.fastframe.module_wan.mvp.contract.LoginContract
import com.vension.fastframe.module_wan.mvp.presenter.LoginPresenter
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_login.*
import kv.vension.vframe.VFrame
import kv.vension.vframe.core.mvp.AbsCompatMVPFragment
import kv.vension.vframe.ext.showToast
import org.greenrobot.eventbus.EventBus

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/2 16:56
 * 描  述：登录 -> wanvension wan123456
 * ========================================================
 */

class LoginFragment : AbsCompatMVPFragment<LoginContract.View, LoginPresenter>(), LoginContract.View {

    private var user: String by PreferenceUtil(Constant.USERNAME_KEY, "")
    private var pwd: String by PreferenceUtil(Constant.PASSWORD_KEY, "")
    private var token: String by PreferenceUtil(Constant.TOKEN_KEY, "")
    private var isLogin: Boolean by PreferenceUtil(Constant.LOGIN_KEY, false)

    override fun enableNetworkTip(): Boolean = false

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_login
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        super.initToolBar(mCommonTitleBar)
        mCommonTitleBar.run {
            setBackgroundColor(VFrame.getColor(R.color.colorWanMain))
            centerTextView.text = "登录"
        }
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        et_username.setText(user)
        btn_login.setOnClickListener(onClickListener)
        tv_sign_up.setOnClickListener(onClickListener)
    }

    override fun lazyLoadData() {
    }

    override fun createPresenter(): LoginPresenter {
        return  LoginPresenter()
    }

    override fun loginSuccess(data: LoginData) {
        showToast(getString(R.string.login_success))
        isLogin = true
        user = data.username
        pwd = data.password
        token = data.token

        EventBus.getDefault().post(LoginEvent(true))
        activity!!.finish()
    }

    override fun loginFail() {
        showToast(getString(R.string.login_fail))
    }

    /**
     * OnClickListener
     */
    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_login -> {
                login()
            }
            R.id.tv_sign_up -> {
                startProxyActivity(RegisterFragment::class.java)
                activity!!.finish()
                activity!!.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    /**
     * Login
     */
    private fun login() {
        if (validate()) {
            mPresenter?.loginWanAndroid(et_username.text.toString(), et_password.text.toString())
        }
    }

    /**
     * Check UserName and PassWord
     */
    private fun validate(): Boolean {
        var valid = true
        val username: String = et_username.text.toString()
        val password: String = et_password.text.toString()

        if (username.isEmpty()) {
            et_username.error = getString(R.string.username_not_empty)
            valid = false
        }
        if (password.isEmpty()) {
            et_password.error = getString(R.string.password_not_empty)
            valid = false
        }
        return valid

    }


}