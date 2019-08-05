package com.vension.fastframe.module_news.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.vension.fastframe.module_news.NewsConstant
import com.vension.fastframe.module_news.R
import com.vension.fastframe.module_news.bean.HomeNewsTabBean
import kotlinx.android.synthetic.main.fragment_tab_home_news.*
import kv.vension.vframe.VFrame
import kv.vension.vframe.core.AbsCompatFragment
import kv.vension.vframe.core.adapter.BaseFragmentStatePagerAdapter
import kv.vension.vframe.ext.showToast
import org.jetbrains.anko.textColor
import java.util.*

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/31 17:10.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/31 17:10
 * @desc:
 * ===================================================================
 */
open class TabHomeFragment : AbsCompatFragment(){

    private val tabList = ArrayList<String>()
    private val fragmentList = ArrayList<Fragment>()

    companion object{
        fun newInstance() : TabHomeFragment{
            return TabHomeFragment()
        }
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_tab_home_news
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        init()
        iv_AddChannel.setOnClickListener(this)
    }

    private fun init() {
        tabList?.clear()
        fragmentList?.clear()
        val tabCurrentShowData: List<HomeNewsTabBean.Data.My>? = NewsConstant.getTabCurrentShowData(0, activity!!)
        if (tabCurrentShowData != null && tabCurrentShowData!!.isNotEmpty()) {
            for (i in tabCurrentShowData!!.indices) {
                fragmentList.add(HomeRecommendFragment.getInstance(tabCurrentShowData!![i].name, i))
                //装载标签数据
                tabList.add(tabCurrentShowData!![i].name)
            }
        }

        val myViewPageAdapter = BaseFragmentStatePagerAdapter(childFragmentManager,fragmentList, tabList)
        vp_tab_home.run {
            adapter = myViewPageAdapter
            offscreenPageLimit = fragmentList.size
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tl_tabHome))
        }

        tl_tabHome.run {
            setupWithViewPager(vp_tab_home)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(vp_tab_home))
            addOnTabSelectedListener(onTabSelectedListener)
        }
    }

    /**
     * onTabSelectedListener
     */
    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            val view = tab?.customView
            if (null == view) {
                tab?.setCustomView(R.layout.view_tab)
            }
            val viewById = tab?.customView!!.findViewById(R.id.tab_text) as TextView
            viewById.isSelected = false
            viewById.textSize = NewsConstant.HOME_TAB_UNSELECTED_SIZE
            viewById.textColor = VFrame.getColor(R.color.color_white)
            viewById.text = tab?.text
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            // 默认切换的时候，会有一个过渡动画，设为false后，取消动画，直接显示
            tab?.let {
                vp_tab_home.setCurrentItem(it.position, false)
                val view = tab?.customView
                if (null == view) {
                    tab?.setCustomView(R.layout.view_tab)
                }
                val viewById = tab.customView!!.findViewById(R.id.tab_text) as TextView
                viewById.isSelected = true
                viewById.textSize = NewsConstant.HOME_TAB_SELECTED_SIZE
                viewById.textColor = VFrame.getColor(R.color.color_white)
                viewById.text = tab?.text
            }
        }
    }



    override fun lazyLoadData() {

    }


    override fun onClick(v: View?) {
        super.onClick(v)
        when(v?.id){
            R.id.iv_AddChannel ->{
               showToast("频道管理")
            }
        }
    }


}