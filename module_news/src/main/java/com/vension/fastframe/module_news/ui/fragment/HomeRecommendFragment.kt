package com.vension.fastframe.module_news.ui.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import cn.jzvd.Jzvd
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.fastframe.module_news.NewsConstant
import com.vension.fastframe.module_news.R
import com.vension.fastframe.module_news.bean.HomeTophotIndex
import com.vension.fastframe.module_news.mvp.contract.HomeRecommendContract
import com.vension.fastframe.module_news.mvp.presenter.HomeRecommendPresenter
import com.vension.fastframe.module_news.ui.adapter.HomeRecommendAdapter
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kv.vension.vframe.VFrame
import kv.vension.vframe.core.mvp.AbsCompatMVPRefreshFragment
import kv.vension.vframe.utils.DensityUtil

/**
 * ===================================================================
 * @author: Created by Vension on 2019/8/2 13:03.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/8/2 13:03
 * @desc:
 * ===================================================================
 */
class HomeRecommendFragment : AbsCompatMVPRefreshFragment<HomeTophotIndex.Articles, HomeRecommendContract.View, HomeRecommendPresenter>(),
    HomeRecommendContract.View {

    private val datas = ArrayList<HomeTophotIndex.Articles>()
    private var pageFrType = 0
    private var distanceY = 0
    private var DISTANCE_WHEN_TO_SELECTED = 40 //改变titlebar中icon颜色时的距离

    companion object{
        fun getInstance(title: String, homeHotType: Int): HomeRecommendFragment {
            val fragment = HomeRecommendFragment()
            fragment.pageFrType = homeHotType
            return fragment
        }
    }


    override fun isToolbarCover(): Boolean {
        return true
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar, title: String) {
        mCommonTitleBar.run {
            leftImageButton.visibility = View.GONE
            setBackgroundColor(VFrame.getColor(R.color.colorNewsMain))
            alpha = 0.0f
        }
    }

    override fun createRecyItemDecoration(): RecyclerView.ItemDecoration? {
        return null
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        super.initViewAndData(view, savedInstanceState)
        getRecyclerView().addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                distanceY += dy
                if (distanceY > DensityUtil.dp2px( 20)) {
                    if (Build.VERSION.SDK_INT > 10) {
                        mCommonTitleBar.alpha = distanceY * 1.0f / DensityUtil.dp2px( 100)
                    } else {
                        DISTANCE_WHEN_TO_SELECTED = 20
                    }
                } else {
                    mCommonTitleBar.alpha = 0f
                }
            }

        })

    }


    override fun enableLoadMore(): Boolean {
        return false
    }

    override fun createRecyAdapter(): BaseQuickAdapter<HomeTophotIndex.Articles, BaseViewHolder> {
        return HomeRecommendAdapter(this@HomeRecommendFragment,datas)
    }

    override fun createPresenter(): HomeRecommendPresenter {
        return HomeRecommendPresenter()
    }

    override fun addItemClickListener(mAdapter: BaseQuickAdapter<HomeTophotIndex.Articles, BaseViewHolder>) {
    }

    override fun onTargetRequestApi(isRefresh: Boolean, page: Int, limit: Int) {
        mPresenter.getHomeHotTopData(NewsConstant.FLAG_NEWS, pageFrType)
    }

    override fun setHomeHotTopData(result: HomeTophotIndex) {
        val heads = ArrayList<View>()
        if(pageFrType != 0){
            val inflate = LayoutInflater.from(activity).inflate(R.layout.item_headerview, null, false)
            heads.add(inflate)
        }
        if (result != null) {
            setRefreshData(result.articles,heads,null)
        }
    }

    override fun onFragmentInVisible() {
        super.onFragmentInVisible()
        if (pageFrType === 2) {
            Jzvd.releaseAllVideos()
        }
    }

}
