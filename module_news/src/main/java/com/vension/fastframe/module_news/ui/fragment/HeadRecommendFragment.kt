package com.vension.fastframe.module_news.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.vension.fastframe.module_news.NewsConstant.FAILED
import com.vension.fastframe.module_news.NewsConstant.SUCCESS
import com.vension.fastframe.module_news.R
import com.vension.fastframe.module_news.bean.FrontNewsModel
import com.vension.fastframe.module_news.bean.NewsMainModel
import com.vension.fastframe.module_news.mvp.contract.HeadContract
import com.vension.fastframe.module_news.mvp.presenter.HeadPresenter
import com.vension.fastframe.module_news.ui.adapter.FrontNewsAdapter
import com.vension.fastframe.module_news.widget.loadmore.CustomLoadMoreView
import kotlinx.android.synthetic.main.fragment_head_recommend.*
import kv.vension.fastframe.core.mvp.AbsCompatMVPFragment
import kv.vension.fastframe.utils.StatusBarUtil
import java.util.*

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/31 11:23.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/31 11:23
 * @desc:
 * ===================================================================
 */
class HeadRecommendFragment : AbsCompatMVPFragment<HeadContract.View, HeadPresenter>(),
    HeadContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    /**
     * 加载要闻样式标记
     */
    private var flag = 1
    private val frontNewsData = ArrayList<FrontNewsModel.Articles>()
    private var adapter: FrontNewsAdapter? = null
    private var intSize = 0

    override fun showToolBar(): Boolean {
        return false
    }

    override fun createPresenter(): HeadPresenter {
        return HeadPresenter()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_head_recommend
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        StatusBarUtil.setLightMode(activity!!)
        iniRecyclerView()
        mYPXQQRefreshView.setRefreshListener {
            handler.postDelayed({
                adapter!!.setEnableLoadMore(false)//这里的作用是防止下拉刷新的时候还可以上拉加载
                mPresenter.getFrontNewsData(flag)
                if (flag == 0) {
                    flag = 1
                } else {
                    flag = 0
                }
            }, 1000)
        }
    }

    private fun iniRecyclerView() {
        adapter = FrontNewsAdapter(activity, R.layout.item_head_recommend, frontNewsData)
        adapter!!.setOnLoadMoreListener(this)
        adapter!!.setEnableLoadMore(true)
        adapter!!.openLoadAnimation()
        adapter!!.setLoadMoreView(CustomLoadMoreView())
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = adapter
    }

    override fun lazyLoadData() {
//        mPresenter.getNewChannels()
        mPresenter.getFrontNewsData(flag)
        flag = 0
    }

    /**
     * 加载更多
     */
    override fun onLoadMoreRequested() {
        mRecyclerView.postDelayed(Runnable {
            if (adapter!!.data.size >= intSize * 2) {
                adapter!!.loadMoreEnd(false)
            } else {
                mPresenter.getFrontNewsMoreWares()
            }
        }, 1000)
    }


    override fun setNewChannels(channes: NewsMainModel) {
        if (channes != null) {
            val t1348647909107 = channes.getT1348647909107().get(3)
        }
    }

    /**
     * 要闻初始化数据
     *
     * @param data
     */
    override fun setFrontNewsData(data: FrontNewsModel) {
        if (data == null) {
            handler.sendEmptyMessage(FAILED)
            return
        }
        adapter!!.data.clear()
        adapter!!.setNewData(data.getArticles())
        adapter!!.notifyDataSetChanged()
        intSize = data.getArticles().size
        handler.sendEmptyMessage(SUCCESS)
    }

    override fun setFrontNewsMoreWares(data: FrontNewsModel) {
        if (data != null) {
            if (data.getArticles() != null) {
                adapter!!.data.addAll(data.getArticles())
                adapter!!.loadMoreComplete()
            }
        }
    }


    @SuppressLint("HandlerLeak")
    internal var handler: Handler = object : Handler() {
        override fun handleMessage(msg: android.os.Message) {
            when (msg.what) {
                SUCCESS -> mYPXQQRefreshView.finishRefresh(true)
                FAILED -> mYPXQQRefreshView.finishRefresh(false)
                else -> {
                }
            }
        }
    }


}