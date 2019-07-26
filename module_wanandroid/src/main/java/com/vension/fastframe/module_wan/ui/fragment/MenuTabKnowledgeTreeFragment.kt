package com.vension.fastframe.module_wan.ui.fragment

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.fastframe.module_wan.Constant
import com.vension.fastframe.module_wan.bean.KnowledgeTreeBody
import com.vension.fastframe.module_wan.mvp.contract.KnowledgeTreeContract
import com.vension.fastframe.module_wan.mvp.presenter.KnowledgeTreePresenter
import com.vension.fastframe.module_wan.ui.adapter.KnowledgeTreeAdapter
import com.vension.mvpforkotlin.sample.ui.fragment.KnowledgeTabFragment
import kv.vension.vframe.core.mvp.AbsCompatMVPRefreshFragment

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/20 15:45
 * 描  述：MenuTab - 知识体系
 * ========================================================
 */

class MenuTabKnowledgeTreeFragment : AbsCompatMVPRefreshFragment<KnowledgeTreeBody, KnowledgeTreeContract.View, KnowledgeTreePresenter>(),
    KnowledgeTreeContract.View {

    companion object {
        fun getInstance(): MenuTabKnowledgeTreeFragment = MenuTabKnowledgeTreeFragment()
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun enableLoadMore(): Boolean {
        return false
    }

    override fun createPresenter(): KnowledgeTreePresenter {
        return KnowledgeTreePresenter()
    }

    override fun createRecyAdapter(): KnowledgeTreeAdapter {
        return KnowledgeTreeAdapter(activity)
    }

    override fun addItemClickListener(mAdapter: BaseQuickAdapter<KnowledgeTreeBody, BaseViewHolder>) {
        mAdapter?.setOnItemClickListener { adapter, view, position ->
            val item  = mAdapter?.getItem(position) as KnowledgeTreeBody
            item?.let {
                val mBundle = getBundleExtras()
                mBundle.putString(Constant.CONTENT_TITLE_KEY,it.name)
                mBundle.putSerializable(Constant.CONTENT_DATA_KEY,it)
                startProxyActivity(KnowledgeTabFragment::class.java,mBundle)
            }
        }
    }

    override fun onTargetRequestApi(isRefresh: Boolean, page: Int, limit: Int) {
        mPresenter?.requestKnowledgeTree()
    }


}