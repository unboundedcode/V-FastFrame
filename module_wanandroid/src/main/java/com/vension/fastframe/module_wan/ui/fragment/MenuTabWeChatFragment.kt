package com.vension.mvpforkotlin.sample.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.vension.fastframe.module_wan.R
import com.vension.fastframe.module_wan.bean.WXChapterBean
import com.vension.fastframe.module_wan.mvp.contract.WeChatContract
import com.vension.fastframe.module_wan.mvp.presenter.WeChatPresenter
import com.vension.fastframe.module_wan.ui.fragment.KnowledgeFragment
import kotlinx.android.synthetic.main.fragment_menu_tab_wechat.*
import kv.vension.vframe.core.adapter.BaseFragmentStatePagerAdapter
import kv.vension.vframe.core.mvp.AbsCompatMVPFragment


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/23 10:33
 * 描  述：MenuTab - 公众号
 * ========================================================
 */

class MenuTabWeChatFragment : AbsCompatMVPFragment<WeChatContract.View, WeChatPresenter>(), WeChatContract.View {

    companion object {
        fun getInstance(): MenuTabWeChatFragment = MenuTabWeChatFragment()
    }

    private var datas = mutableListOf<WXChapterBean>()
    private var fragments = mutableListOf<Fragment>()
    private var titles = mutableListOf<String>()

    private val viewPagerAdapter: BaseFragmentStatePagerAdapter by lazy {
        BaseFragmentStatePagerAdapter(getPageFragmentManager(),fragments,titles)
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun createPresenter(): WeChatPresenter {
        return WeChatPresenter()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_menu_tab_wechat
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        viewPager_wechat.run {
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout_wechat))
        }

        tabLayout_wechat.run {
            setupWithViewPager(viewPager_wechat)
//             TabLayoutHelper.setUpIndicatorWidth(tabLayout)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager_wechat))
            addOnTabSelectedListener(onTabSelectedListener)
        }

    }

    override fun lazyLoadData() {
        mPresenter?.getWXChapters()
    }

    override fun doReConnected() {
        if (datas.size == 0) {
            super.doReConnected()
        }
    }

    override fun showWXChapters(chapters: MutableList<WXChapterBean>) {
        chapters.let {
            datas.addAll(it)
            datas.forEach{
                fragments.add(KnowledgeFragment.getInstance(it.id))
                titles.add(it.name)
            }
            viewPager_wechat.run {
                adapter = viewPagerAdapter
                offscreenPageLimit = datas.size
            }
        }
    }

    /**
     * onTabSelectedListener
     */
    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            // 默认切换的时候，会有一个过渡动画，设为false后，取消动画，直接显示
            tab?.let {
                viewPager_wechat.setCurrentItem(it.position, false)
            }
        }
    }


}