package com.vension.fastframe.module_course.ui.fragment

import android.os.Bundle
import android.view.View
import com.vension.fastframe.module_course.R
import com.vension.fastframe.module_course.bean.MineBean
import com.vension.fastframe.module_course.mvp.contract.MineContract
import com.vension.fastframe.module_course.mvp.presenter.MinePresenter
import kotlinx.android.synthetic.main.fragment_tab_mine.*
import kv.vension.vframe.core.mvp.AbsCompatMVPFragment
import kv.vension.vframe.utils.OnInterceptDoubleClickListener
import kv.vension.vframe.utils.StatusBarUtil

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/25 11:20.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/25 11:20
 * @desc:   character determines attitude, attitude determines destiny
 * ===================================================================
 */
class TabMineFragment : AbsCompatMVPFragment<MineContract.View,MinePresenter>(),MineContract.View {

    companion object {
        fun newInstance(): TabMineFragment {
            return TabMineFragment()
        }
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun createPresenter(): MinePresenter {
        return MinePresenter()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_tab_mine
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        StatusBarUtil.setLightMode(activity!!)
        layout_cacheRecord.setOnClickListener(object : OnInterceptDoubleClickListener(){
            override fun onInterceptDoubleClick(v: View?) {
                startProxyActivity(CacheRecordFragment::class.java)
            }
        })
    }

    override fun lazyLoadData() {
    }

    override fun showMine(mineBean: MineBean) {
    }

}