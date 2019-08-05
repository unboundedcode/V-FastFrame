package com.vension.fastframe.module_news.ui.fragment

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.fastframe.module_news.R
import com.vension.fastframe.module_news.bean.HotNewsBestModel
import com.vension.fastframe.module_news.mvp.contract.HotNewsBestContract
import com.vension.fastframe.module_news.mvp.presenter.HotNewsBestPresenter
import com.vension.fastframe.module_news.ui.adapter.HotNewsBestMultipleRecycleAdapter
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kv.vension.vframe.VFrame
import kv.vension.vframe.core.mvp.AbsCompatMVPRefreshFragment
import java.util.*

/**
 * ===================================================================
 * @author: Created by Vension on 2019/8/5 10:08.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/8/5 10:08
 * @desc:   热闻精选详情界面
 * ===================================================================
 */
class HotNewsBestFragment : AbsCompatMVPRefreshFragment<HotNewsBestModel.Articles,HotNewsBestContract.View,HotNewsBestPresenter>(),HotNewsBestContract.View {


    private val datas = ArrayList<HotNewsBestModel.Articles>()

    override fun initToolBar(mCommonTitleBar: CommonTitleBar, title: String) {
        super.initToolBar(mCommonTitleBar, title)
        mCommonTitleBar.run {
            setBackgroundColor(VFrame.getColor(R.color.colorNewsMain))
            centerTextView.background = VFrame.getDrawable(R.drawable.img_hotnews_title)
        }
    }

    override fun enableLoadMore(): Boolean {
        return false
    }

    override fun createRecyItemDecoration(): RecyclerView.ItemDecoration? {
        return null
    }

    override fun createRecyAdapter(): BaseQuickAdapter<HotNewsBestModel.Articles, BaseViewHolder> {
        return HotNewsBestMultipleRecycleAdapter(datas)
    }

    override fun onTargetRequestApi(isRefresh: Boolean, page: Int, limit: Int) {
        mPresenter.getHotNewsBestData()
    }

    override fun createPresenter(): HotNewsBestPresenter {
        return HotNewsBestPresenter()
    }

    override fun setHotNewsBestData(hotNewsBestModel: HotNewsBestModel?) {
        if (hotNewsBestModel != null) {
            setRefreshData(hotNewsBestModel.articles)
        }
    }

}