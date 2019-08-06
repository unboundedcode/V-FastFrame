package com.vension.fastframe.module_news.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import cn.jzvd.Jzvd
import com.flyco.tablayout.listener.OnTabSelectListener
import com.vension.fastframe.module_news.NewsConstant
import kotlinx.android.synthetic.main.fragment_tab_video.*
import kv.vension.fastframe.core.AbsCompatFragment
import kv.vension.fastframe.core.adapter.BaseFragmentStatePagerAdapter
import kv.vension.fastframe.utils.StatusBarUtil
import java.util.*



/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/30 11:52.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/30 11:52
 * @desc:
 * ===================================================================
 */
class TabVideoFragment : AbsCompatFragment(), ViewPager.OnPageChangeListener, OnTabSelectListener {

    private val mFragments = ArrayList<Fragment>()

    companion object{
        fun newInstance() : TabVideoFragment{
            return TabVideoFragment()
        }
    }

    override fun showToolBar(): Boolean {
        return false
    }
    override fun attachLayoutRes(): Int {
        return com.vension.fastframe.module_news.R.layout.fragment_tab_video
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        StatusBarUtil.setLightMode(activity!!)
        val  titles = NewsConstant.getVideoCategoryStr()
        if (titles.isNotEmpty()) {
            for (i in 0 until titles.size) {
                mFragments.add(VideoListFragment.getInstance(titles[i], i))
            }
        }

        newsViewpager.adapter = BaseFragmentStatePagerAdapter(getPageFragmentManager(),mFragments,titles)
        newsViewpager.offscreenPageLimit = 3

        slidingTabLayout.setViewPager(newsViewpager, titles.toTypedArray())
        slidingTabLayout.currentTab = 0
        //显示未读红点
        slidingTabLayout.showDot(1)
        slidingTabLayout.showMsg(5, 2)
        slidingTabLayout.setMsgMargin(5, 0f, 10f)

        newsViewpager.currentItem = 0

        newsViewpager.addOnPageChangeListener(this)
        slidingTabLayout.setOnTabSelectListener(this)
    }

    override fun lazyLoadData() {

    }


    /**
     * 界面滑动状态改变
     * @param state
     */
    override fun onPageScrollStateChanged(state: Int) {
    }

    /**
     * 界面滑动
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }
    /**
     * 界面滑动停止选中界面
     * @param position
     */
    override fun onPageSelected(position: Int) {
        slidingTabLayout.currentTab = position
        //切换界面，停止所有播放状态
        Jzvd.releaseAllVideos()
    }

    /**
     * 页签选中
     * @param position
     */
    override fun onTabSelect(position: Int) {
        newsViewpager.currentItem = position
    }

    /**
     * 页签复选
     * @param position
     */
    override fun onTabReselect(position: Int) {
    }

    /**
     * 当前界面是否隐藏
     * 这种方法适用于界面替换用到hide()和show()方法
     * @param hidden
     */
    override fun onFragmentInVisible() {
        super.onFragmentInVisible()
        Jzvd.releaseAllVideos()//展示，可见  // 相当于Fragment的onResume()
    }


}