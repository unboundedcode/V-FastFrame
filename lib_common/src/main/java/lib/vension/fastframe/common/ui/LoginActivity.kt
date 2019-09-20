package lib.vension.fastframe.common.ui

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_login.*
import kv.vension.fastframe.core.mvp.AbsCompatMVPActivity
import kv.vension.fastframe.utils.SPUtil
import kv.vension.fastframe.utils.ToastHelper
import lib.vension.fastframe.common.Constant
import lib.vension.fastframe.common.R
import lib.vension.fastframe.common.mvp.LoginContract
import lib.vension.fastframe.common.mvp.LoginPresenter
import lib.vension.fastframe.common.router.RouterConfig

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/18 11:49.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/18 11:49
 * @desc:   character determines attitude, attitude determines destiny
 * ===================================================================
 */

@Route(path = RouterConfig.PATH_COMMON_LOGINACTIVITY)
class LoginActivity : AbsCompatMVPActivity<LoginContract.View, LoginContract.Presenter>(),LoginContract.View {

    private var targetUrl: String? = null

    override fun createPresenter(): LoginContract.Presenter {
        return LoginPresenter()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.activity_login
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        targetUrl = getBundleExtras().getString("targetUrl")
    }

    override fun loginSuccess(data: String) {
        //跳转到目标页
        ToastHelper.success("登录成功",true).show()
        SPUtil.put(Constant.IS_LOGIN,true)
        targetUrl?.let {
            ARouter.getInstance()
                .build(it)
                .withString("data", data)
                .navigation()
            finish()
        }
    }


    fun onClick(view: View) {
        val id = view.id
        if (id == R.id.login_btn) {
            val account = account_edt.text!!.trim().toString()
            val password = password_edt.text!!.trim().toString()
            if (account.isEmpty()) {
                account_edt.error = "请输入账号"
                return
            }
            if (password.isEmpty()) {
                password_edt.error = "请输入密码"
                return
            }
            mPresenter.doLogin()
        }
    }

}