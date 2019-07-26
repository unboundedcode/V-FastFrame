package com.vension.fastframe.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.vension.fastframe.app.R
import kotlinx.android.synthetic.main.fragment_net_test.*
import kv.vension.vframe.core.AbsCompatFragment

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/16 11:33.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/16 11:33
 * @desc:   character determines attitude, attitude determines destiny
 * ===================================================================
 */
class NetTestFragment : AbsCompatFragment(){

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_net_test
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        btnObservable.setOnClickListener {
            startProxyActivity(ObservableFragment::class.java)
        }
        btnFlowable.setOnClickListener {
            startProxyActivity(FlowableFragment::class.java)
        }
        btnT.setOnClickListener {
            startProxyActivity(TFragment::class.java)
        }
    }

    override fun lazyLoadData() {
    }
}