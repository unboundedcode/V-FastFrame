package com.vension.fastframe.module_wan.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vension.fastframe.module_wan.R
import com.vension.fastframe.module_wan.bean.Article
import com.vension.fastframe.module_wan.bean.NavigationBean
import com.vension.fastframe.module_wan.mvp.contract.NavigationContract
import com.vension.fastframe.module_wan.mvp.presenter.NavigationPresenter
import com.vension.fastframe.module_wan.ui.adapter.NavigationAdapter
import com.vension.fastframe.module_wan.ui.adapter.NavigationTabAdapter
import kotlinx.android.synthetic.main.fragment_menu_tab_navigation.*
import kv.vension.vframe.core.mvp.AbsCompatMVPFragment
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.widget.TabView

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/22 16:40.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */ 
class MenuTabNavigationFragment: AbsCompatMVPFragment<NavigationContract.View, NavigationPresenter>(),
    NavigationContract.View{

    companion object {
        fun getInstance(): MenuTabNavigationFragment = MenuTabNavigationFragment()
    }

    private var bScroll: Boolean = false
    private var currentIndex: Int = 0
    private var bClickTab: Boolean = false

    /**
     * datas
     */
    private var datas = mutableListOf<NavigationBean>()

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    private val navigationAdapter: NavigationAdapter by lazy {
        NavigationAdapter(activity, datas)
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_menu_tab_navigation
    }

    override fun createPresenter(): NavigationPresenter {
        return NavigationPresenter()
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        recyclerView_navigation.run {
            layoutManager = linearLayoutManager
            adapter = navigationAdapter
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }

        navigationAdapter.run {
            bindToRecyclerView(recyclerView_navigation)
            addItemClickListener(object : NavigationAdapter.OnItemClickListener{
                override fun onItemClick(obj: Article) {
                    obj?.let {
                        val mBundle = getBundleExtras()
                        mBundle.putString("web_url",it.link)
                        mBundle.putString("web_title",it.title)
                        mBundle.putInt("web_id",it.id)
                        startProxyActivity(AgentWebFragment::class.java,mBundle)
                    }
                }
            })
        }

        leftRightLink()
    }

    /**
     * Left TabLayout and Right RecyclerView Link
     */
    private fun leftRightLink() {
        recyclerView_navigation.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (bScroll && (newState == RecyclerView.SCROLL_STATE_IDLE)) {
                    scrollRecyclerView()
                }
                rightLinkLeft(newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (bScroll) {
                    scrollRecyclerView()
                }
            }
        })

        mVerticalTabLayout.addOnTabSelectedListener(object : VerticalTabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabView?, position: Int) {
            }

            override fun onTabSelected(tab: TabView?, position: Int) {
                bClickTab = true
                selectTab(position)
            }
        })

    }


    override fun lazyLoadData() {
        mPresenter?.requestNavigationList()
    }

    override fun setNavigationData(list: List<NavigationBean>) {
        list.let {
            mVerticalTabLayout.run {
                setTabAdapter(NavigationTabAdapter(context, list))
            }
            navigationAdapter.run {
                replaceData(it)
                loadMoreComplete()
                loadMoreEnd()
                setEnableLoadMore(false)
            }
        }
    }


    private fun scrollRecyclerView() {
        bScroll = false
        val indexDistance: Int = currentIndex - linearLayoutManager.findFirstVisibleItemPosition()
        if (indexDistance > 0 && indexDistance < recyclerView_navigation!!.childCount) {
            val top: Int = recyclerView_navigation.getChildAt(indexDistance).top
            recyclerView_navigation.smoothScrollBy(0, top)
        }
    }

    /**
     * Right RecyclerView link Left TabLayout
     *
     * @param newState RecyclerView Scroll State
     */
    private fun rightLinkLeft(newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (bClickTab) {
                bClickTab = false
                return
            }
            val firstPosition: Int = linearLayoutManager.findFirstVisibleItemPosition()
            if (firstPosition != currentIndex) {
                currentIndex = firstPosition
                setChecked(currentIndex)
            }
        }
    }

    /**
     * Smooth Right RecyclerView to Select Left TabLayout
     *
     * @param position checked position
     */
    private fun setChecked(position: Int) {
        if (bClickTab) {
            bClickTab = false
        } else {
            mVerticalTabLayout.setTabSelected(currentIndex)
        }
        currentIndex = position
    }

    /**
     * Select Left TabLayout to Smooth Right RecyclerView
     */
    private fun selectTab(position: Int) {
        currentIndex = position
        recyclerView_navigation.stopScroll()
        smoothScrollToPosition(position)
    }

    private fun smoothScrollToPosition(position: Int) {
        val firstPosition: Int = linearLayoutManager.findFirstVisibleItemPosition()
        val lastPosition: Int = linearLayoutManager.findLastVisibleItemPosition()
        when {
            position <= firstPosition -> {
                recyclerView_navigation.smoothScrollToPosition(position)
            }
            position <= lastPosition -> {
                val top: Int = recyclerView_navigation.getChildAt(position - firstPosition).top
                recyclerView_navigation.smoothScrollBy(0, top)
            }
            else -> {
                recyclerView_navigation.smoothScrollToPosition(position)
                bScroll = true
            }
        }
    }


    fun scrollToTop() {
        mVerticalTabLayout.setTabSelected(0)
    }



}