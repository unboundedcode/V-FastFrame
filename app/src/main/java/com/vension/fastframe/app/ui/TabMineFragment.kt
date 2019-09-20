package com.vension.fastframe.app.ui

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.vension.fastframe.app.R
import kotlinx.android.synthetic.main.fragment_tab_mine.*
import kv.vension.fastframe.core.AbsCompatFragment
import kv.vension.fastframe.utils.SPUtil
import kv.vension.fastframe.utils.ToastHelper
import lib.vension.fastframe.common.Constant
import lib.vension.fastframe.common.router.RouterConfig
import lib.vension.fastframe.common.router.interceptor.LoginNavigationCallbackImpl

/**
 * ========================================================================
 * @author: Created by Vension on 2019/9/20 14:43.
 * @email : vensionHu@qq.com
 * @Github: https://github.com/Vension
 * __      ________ _   _  _____ _____ ____  _   _
 * \ \    / /  ____| \ | |/ ____|_   _/ __ \| \ | |
 *  \ \  / /| |__  |  \| | (___   | || |  | |  \| |
 *   \ \/ / |  __| | . ` |\___ \  | || |  | | . ` |
 *    \  /  | |____| |\  |____) |_| || |__| | |\  |
 *     \/   |______|_| \_|_____/|_____\____/|_| \_|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * @desc: happy code ->
 * ========================================================================
 */
class TabMineFragment : AbsCompatFragment() {

    companion object {
        fun newInstance(): TabMineFragment {
            return TabMineFragment()
        }
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_tab_mine
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        btn_login.setOnClickListener(this)
        btn_first.setOnClickListener(this)
        btn_second.setOnClickListener(this)
        btn_exit.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        super.onClick(v)
        when(v?.id){
            R.id.btn_login -> {
                ARouter.getInstance().build(RouterConfig.PATH_COMMON_LOGINACTIVITY)
                    .navigation()
            }
            R.id.btn_first -> {
                ARouter.getInstance().build(RouterConfig.PATH_VIEW_MAINACTIVITY).navigation()
            }
            R.id.btn_second -> {
                ARouter.getInstance().build(RouterConfig.PATH_WAN_MAINACTIVITY)
                    .withString("msg", "ARouter传递过来的需要登录的参数msg")
                    .navigation(mContext, LoginNavigationCallbackImpl())
            }
            R.id.btn_exit -> {
                SPUtil.remove(Constant.IS_LOGIN)
                ToastHelper.normal("退出登录成功").show()
            }
        }
    }
    override fun lazyLoadData() {
    }




}