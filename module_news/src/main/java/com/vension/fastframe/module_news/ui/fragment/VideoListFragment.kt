package com.vension.fastframe.module_news.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.vension.fastframe.module_news.NewsConstant
import com.vension.fastframe.module_news.R
import com.vension.fastframe.module_news.bean.VideoNewsModel
import com.vension.fastframe.module_news.mvp.contract.VideoContract
import com.vension.fastframe.module_news.mvp.presenter.VideoPresenter
import com.vension.fastframe.module_news.ui.adapter.VideoNewsAdapter
import com.vension.fastframe.module_news.widget.loadmore.CustomLoadMoreView
import kotlinx.android.synthetic.main.fragment_video_list.*
import kv.vension.vframe.VFrame
import kv.vension.vframe.core.mvp.AbsCompatMVPFragment
import java.util.*

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/30 14:28.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/30 14:28
 * @desc:
 * ===================================================================
 */
class VideoListFragment : AbsCompatMVPFragment<VideoContract.View,VideoPresenter>()
    ,VideoContract.View, OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private var intSize = 0
    /**
     * 加载样式标记
     */
    private var flag = 1
    private var videoFrType = 0
    private var mAdapter: VideoNewsAdapter? = null
    private val data = ArrayList<VideoNewsModel.Articles>()

    companion object {
        fun getInstance(title: String, i: Int): VideoListFragment {
            val fragment = VideoListFragment()
            fragment.videoFrType = i
            return fragment
        }
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_video_list
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        iniRecyclerView()
        swipe_refresh_header.setBackgroundColor(VFrame.getColor(R.color.color_white))
        swipeToLoadLayout.setOnRefreshListener(this)
        swipe_target.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)) {
                        swipeToLoadLayout.isLoadingMore = true
                    }
                }
            }
        })

    }

    private fun iniRecyclerView() {
        mAdapter = VideoNewsAdapter(activity, R.layout.item_video_home, data)
        mAdapter?.setOnLoadMoreListener(this)
        mAdapter?.setEnableLoadMore(true)
        mAdapter?.openLoadAnimation()
        mAdapter?.setLoadMoreView(CustomLoadMoreView())
        swipe_target.layoutManager = LinearLayoutManager(activity)
        swipe_target.adapter = mAdapter
    }


    override fun lazyLoadData() {
        onRefresh()
    }


    override fun createPresenter(): VideoPresenter {
        return VideoPresenter()
    }

    /**
     * 视频数据
     * @param data
     */
    override fun setVideoData(data: VideoNewsModel?) {
        if (data == null) {
            handler.sendEmptyMessage(NewsConstant.FAILED)
            return
        }
        mAdapter!!.data.clear()
        mAdapter!!.setNewData(data.getArticles())
        mAdapter!!.notifyDataSetChanged()
        intSize = data.getArticles().size
        handler.sendEmptyMessage(NewsConstant.SUCCESS)
    }


    /**
     * 加载更多数据
     * @param data
     */
    override fun setVideoMoreWares(data: VideoNewsModel) {
        if (data != null) {
            if (data.getArticles() != null) {
                mAdapter!!.data.addAll(data.getArticles())
                mAdapter!!.loadMoreComplete()
            }
        }
    }

    override fun onRefresh() {
        swipeToLoadLayout.postDelayed(Runnable {
            mAdapter!!.setEnableLoadMore(false)//这里的作用是防止下拉刷新的时候还可以上拉加载
            mPresenter.getVideoData(flag, videoFrType)
            if (flag == 0) {
                flag = 1
            } else {
                flag = 0
            }
        }, 1000)
    }

    /**
     * 加载更多监听
     */
    override fun onLoadMoreRequested() {
        swipe_target.postDelayed(Runnable {
            if (mAdapter!!.data.size >= intSize * 2) {
                mAdapter?.loadMoreEnd(false)
            } else {
                mPresenter.getVideoMoreWares(videoFrType)
            }
        }, 1000)
    }

    @SuppressLint("HandlerLeak")
    internal var handler: Handler = object : Handler() {
        override fun handleMessage(msg: android.os.Message) {
            when (msg.what) {
                NewsConstant.SUCCESS -> swipeToLoadLayout.isRefreshing = false
                NewsConstant.FAILED -> swipeToLoadLayout.isRefreshing = false
                else -> {
                }
            }
        }
    }

}