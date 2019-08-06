package com.vension.fastframe.module_news.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import cn.jzvd.Jzvd
import com.alibaba.android.arouter.facade.annotation.Route
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.vension.fastframe.module_news.R
import com.vension.fastframe.module_news.bean.TabEntity
import com.vension.fastframe.module_news.ui.fragment.TabHeadNewsFragment
import com.vension.fastframe.module_news.ui.fragment.TabHomeFragment
import com.vension.fastframe.module_news.ui.fragment.TabMineFragment
import com.vension.fastframe.module_news.ui.fragment.TabVideoFragment
import kotlinx.android.synthetic.main.activity_main_news.*
import kv.vension.fastframe.core.AbsCompatActivity
import kv.vension.fastframe.core.adapter.BaseFragmentAdapter
import kv.vension.fastframe.utils.StatusBarUtil
import lib.vension.fastframe.common.RouterConfig
import java.util.*

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/17 20:09.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/17 20:09
 * @desc:   character determines attitude, attitude determines destiny
 * ===================================================================
 */

@Route(path = RouterConfig.PATH_NEWS_MAINACTIVITY)
class NewsMainActivity : AbsCompatActivity() {

    private val mFragments = ArrayList<Fragment>()
    private val mTitles = arrayOf("首页", "要闻", "视频", "我的")
    private val mTabEntities = ArrayList<CustomTabEntity>()
    private val mIconUnselectIds = intArrayOf(
        R.drawable.ic_home_normal,
        R.drawable.ic_importnews_nornal,
        R.drawable.ic_video_normal,
        R.drawable.ic_care_normal
    )
    private val mIconSelectIds = intArrayOf(
        R.drawable.ic_home_selected,
        R.drawable.ic_importnews_selected,
        R.drawable.ic_video_selected,
        R.drawable.ic_care_selected
    )

    override fun attachLayoutRes(): Int {
        return R.layout.activity_main_news
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        for (i in mTitles.indices) {
            if (0 == i) mFragments.add(TabHomeFragment.newInstance())
            if (1 == i) mFragments.add(TabHeadNewsFragment.newInstance())
            if (2 == i) mFragments.add(TabVideoFragment.newInstance())
            if (3 == i) mFragments.add(TabMineFragment.newInstance())
            mTabEntities.add(TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]))
        }
        vp_newsMain.adapter = BaseFragmentAdapter(supportFragmentManager,mFragments, mTitles.toList())
        vp_newsMain.offscreenPageLimit = 4

        tl_2()

        //两位数
        newsCommonTabLayout.showMsg(0, 55)
        newsCommonTabLayout.setMsgMargin(0, -5f, 5f)

        //三位数
        newsCommonTabLayout.showMsg(1, 100)
        newsCommonTabLayout.setMsgMargin(1, -5f, 5f)

        //设置未读消息红点
        newsCommonTabLayout.showDot(2)
        //设置未读消息背景
        newsCommonTabLayout.showMsg(3, 5)
        newsCommonTabLayout.setMsgMargin(3, 0f, 5f)
        val rtv_2_3 = newsCommonTabLayout.getMsgView(3)
        if (rtv_2_3 != null) {
            rtv_2_3!!.backgroundColor = Color.parseColor("#6D8FB0")
        }
    }

    private fun tl_2() {
        StatusBarUtil.setDarkMode(this@NewsMainActivity)
        newsCommonTabLayout.setTabData(mTabEntities)
        newsCommonTabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                vp_newsMain.currentItem = position
                if (0 == position) {
                } else if (1 == position) {
                    val fragment = mFragments[position] as TabHeadNewsFragment
                    if (fragment != null) {
                        StatusBarUtil.setLightMode(this@NewsMainActivity)
                    }
                } else if (2 == position) {
                    val fragment = mFragments[position] as TabVideoFragment
                    if (fragment != null) {
                        StatusBarUtil.setLightMode(this@NewsMainActivity)
                    }
                } else if (3 == position) {
                    val fragment = mFragments[position] as TabMineFragment
                    if (fragment != null) {
                        StatusBarUtil.setDarkMode(this@NewsMainActivity)
                    }
                }
            }

            override fun onTabReselect(position: Int) {
                if (position == 0) {
                    newsCommonTabLayout.showMsg(0, Random().nextInt(100) + 1)
                }
            }
        })
        vp_newsMain.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                newsCommonTabLayout.currentTab = position
            }

        })
        vp_newsMain.currentItem = 0
    }


    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

    /**
     * 监听全屏视频时返回键
     */
    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

}