package com.vension.fastframe.app.ui.tab1

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kv.vension.fastframe.core.mvp.AbsCompatMVPRefreshFragment
import lib.vension.fastframe.common.test.TestBean

/**
 * ===================================================================
 * @author: Created by Vension on 2019/8/6 15:13.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/8/6 15:13
 * @desc:
 * ===================================================================
 */
class TabFragment_1 : AbsCompatMVPRefreshFragment<TestBean, TabContract_1.View, TabPresenter_1>(),
    TabContract_1.View {

    companion object{
        fun newInstance() : TabFragment_1 {
            return TabFragment_1()
        }
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun enableLoadMore(): Boolean {
        return false
    }

    override fun createRecyAdapter(): BaseQuickAdapter<TestBean, BaseViewHolder> {
        val lists = mutableListOf<TestBean>()
        return TabAdapter_1(lists)
    }

    override fun onTargetRequestApi(isRefresh: Boolean, page: Int, limit: Int) {
        mPresenter.getHomeDatas()
    }

    override fun createPresenter(): TabPresenter_1 {
        return TabPresenter_1()
    }

    override fun setHomeDatas(datas: MutableList<TestBean>) {
        setRefreshData(datas)
    }

}