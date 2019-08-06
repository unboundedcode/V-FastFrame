package com.vension.fastframe.module_news.ui.fragment

import android.os.Bundle
import android.view.View
import com.vension.fastframe.module_news.R
import kotlinx.android.synthetic.main.fragment_my_follow.*
import kv.vension.fastframe.core.AbsCompatFragment
import kv.vension.fastframe.ext.showToast

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/31 11:02.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/31 11:02
 * @desc:
 * ===================================================================
 */
class MyHeadFollowFragment : AbsCompatFragment() {

    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_my_follow
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        btn_login.setOnClickListener(this)
    }

    override fun lazyLoadData() {
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v?.id){
            R.id.btn_login -> {
                showToast("去登录")
            }
        }
    }

}