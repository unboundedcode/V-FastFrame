package com.vension.fastframe.module_course.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.vension.fastframe.module_course.R
import com.vension.fastframe.module_course.bean.DiscoveryCommentBean
import com.vension.fastframe.module_course.bean.TabEntity
import com.vension.fastframe.module_course.bean.TagBean
import com.vension.fastframe.module_course.event.RefreshHomeEvent
import com.vension.fastframe.module_course.mvp.contract.MainContract
import com.vension.fastframe.module_course.mvp.presenter.MainPresenter
import com.vension.fastframe.module_course.ui.fragment.TabCourseFragment
import com.vension.fastframe.module_course.ui.fragment.TabHomeFragment
import com.vension.fastframe.module_course.ui.fragment.TabMineFragment
import kotlinx.android.synthetic.main.activity_main_course.*
import kv.vension.fastframe.core.mvp.AbsCompatMVPActivity
import kv.vension.fastframe.ext.Loge
import lib.vension.fastframe.common.RouterConfig
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/25 10:10.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/25 10:10
 * @desc:   character determines attitude, attitude determines destiny
 * ===================================================================
 */
@Route(path = RouterConfig.PATH_COURSE_MAINACTIVITY)
class CourseMainActivity : AbsCompatMVPActivity<MainContract.View, MainPresenter>(), OnTabSelectListener ,MainContract.View{

    private var mCurrentPos: Int = -1
    private val numbers = ArrayList<Int>()
    private var mFragments = mutableListOf<Fragment>()
    private var mTabEntities = ArrayList<CustomTabEntity>()
    private lateinit var stages: DiscoveryCommentBean.Stages
    private lateinit var mTagBean: TagBean
    private var title: String? = null

    override fun attachLayoutRes(): Int {
        return R.layout.activity_main_course
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        //底部tab
        mTabEntities.add(TabEntity("首页",
            R.drawable.tab_discover_checked,
            R.drawable.tab_discover
        ))
        mTabEntities.add(TabEntity("我的课程",
            R.drawable.tab_my_course_checked,
            R.drawable.tab_my_course
        ))
        mTabEntities.add(TabEntity("个人中心",
            R.drawable.tab_mine_checked,
            R.drawable.tab_mine
        ))
        bottom_main.setTabData(mTabEntities)
        bottom_main.setOnTabSelectListener(this)

        loadData()
    }

    override fun onTabReselect(position: Int) {
    }

    override fun onTabSelect(position: Int) {
        //底部按钮切换
        if(mFragments.isNotEmpty()){
            switchFragmentIndex(position)
        }
    }

    private fun switchFragmentIndex(index: Int) {
        //特别注意，fragment重叠问题，mCurrentPos是上一个fragment,index是当前fragment
        supportFragmentManager.beginTransaction()
            .apply {
                if (mCurrentPos != -1)
                    hide(mFragments[mCurrentPos])
                if (!mFragments[index].isAdded) {
                    add(R.id.fl_content, mFragments[index])
                }
                show(mFragments[index]).commit()
                mCurrentPos = index
            }
    }

    private fun loadData() {
        numbers.add(432)
        numbers.add(1228)
        mPresenter.getRegionTagTypeBean(numbers) //初始值默认为中学
    }


    override fun createPresenter(): MainPresenter {
        return MainPresenter()
    }

    override fun showDiscoveryBean(mDiscoveryCommentBean: DiscoveryCommentBean) {
        stages = mDiscoveryCommentBean.stages
        val hostObject = JsonParser().parse(Gson().toJson(stages)).asJsonObject
        var nextId: String? = null
        var result = JsonObject()
        for (stages in stages.`154271985`.subTags) {
            if (numbers[0] == stages.tagId) {
                nextId = stages.nextStage
                break
            }
        }
        for (article in hostObject.keySet()) {
            if (nextId == article) {
                val json = hostObject.getAsJsonObject(article)
                val mSubTags = Gson().fromJson(json, TagBean::class.java)
                for (tag in mSubTags.subTags) {
                    if (numbers[1] == tag.tagId) {
                        title = tag.tagName
                        result = hostObject.getAsJsonObject(tag.nextStage)
                        break
                    }
                }
            }
        }
        mTagBean = Gson().fromJson(result, TagBean::class.java)
    }

    override fun showSetTag() {
        mFragments = mutableListOf(
            TabHomeFragment.newInstance(mTagBean, numbers, title),
            TabCourseFragment.newInstance(),
            TabMineFragment.newInstance())
        switchFragmentIndex(0)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun doRefresh(event: RefreshHomeEvent) {
        if (event.isRefresh) {
            numbers.clear()
            numbers.add(event.tagId)
            numbers.add(event.nextId)
            Loge("onActivityResult-->main")
            mPresenter.getRegionTagTypeBean(numbers)
        }
    }

}