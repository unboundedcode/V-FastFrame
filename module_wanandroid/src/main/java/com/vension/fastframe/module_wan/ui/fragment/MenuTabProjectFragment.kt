package com.vension.mvpforkotlin.sample.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.vension.fastframe.module_wan.R
import com.vension.fastframe.module_wan.bean.ProjectTreeBean
import com.vension.fastframe.module_wan.mvp.contract.ProjectContract
import com.vension.fastframe.module_wan.mvp.presenter.ProjectPresenter
import kotlinx.android.synthetic.main.fragment_menu_tab_project.*
import kv.vension.vframe.core.adapter.BaseFragmentStatePagerAdapter
import kv.vension.vframe.core.mvp.AbsCompatMVPFragment

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/22 15:03
 * 描  述：MenuTab - 项目
 * ========================================================
 */

class MenuTabProjectFragment : AbsCompatMVPFragment<ProjectContract.View, ProjectPresenter>(), ProjectContract.View {

    companion object {
        fun getInstance(): MenuTabProjectFragment = MenuTabProjectFragment()
    }

    private var projectTree = mutableListOf<ProjectTreeBean>()
    private var fragments = mutableListOf<Fragment>()
    private var titles = mutableListOf<String>()

    private val viewPagerAdapter: BaseFragmentStatePagerAdapter by lazy {
        BaseFragmentStatePagerAdapter(getPageFragmentManager(),fragments,titles)
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun createPresenter(): ProjectPresenter {
        return ProjectPresenter()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_menu_tab_project
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        viewPager_project.run {
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout_project))
        }

        tabLayout_project.run {
            setupWithViewPager(viewPager_project)
//             TabLayoutHelper.setUpIndicatorWidth(tabLayout)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager_project))
            addOnTabSelectedListener(onTabSelectedListener)
        }

    }

    override fun lazyLoadData() {
        mPresenter?.requestProjectTree()
    }

    override fun doReConnected() {
        if (projectTree.size == 0) {
            super.doReConnected()
        }
    }

    override fun setProjectTree(list: List<ProjectTreeBean>) {
        list.let {
            projectTree.addAll(it)
            projectTree.forEach{
                fragments.add(ProjectListFragment.getInstance(it.id))
                titles.add(it.name)
            }
            viewPager_project.run {
                adapter = viewPagerAdapter
                offscreenPageLimit = projectTree.size
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
                viewPager_project.setCurrentItem(it.position, false)
            }
        }
    }


}