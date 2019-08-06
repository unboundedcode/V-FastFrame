package com.vension.fastframe.module_course.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vension.fastframe.module_course.R
import com.vension.fastframe.module_course.bean.SelectionBean
import com.vension.fastframe.module_course.mvp.contract.SelectionContract
import com.vension.fastframe.module_course.mvp.presenter.SelectionPresenter
import com.vension.fastframe.module_course.ui.adapter.section.SectionBanner
import com.vension.fastframe.module_course.ui.adapter.section.SectionCourse
import com.vension.fastframe.module_course.ui.adapter.section.SectionSerial
import com.vension.fastframe.module_course.ui.adapter.section.SectionSpecial
import com.vension.fastframe.module_course.widget.section.SectionedRVAdapter
import kotlinx.android.synthetic.main.fragment_section.*
import kv.vension.fastframe.VFrame
import kv.vension.fastframe.core.mvp.AbsCompatMVPFragment

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/25 17:58.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/25 17:58
 * @desc:
 * ===================================================================
 */
class SectionFragment : AbsCompatMVPFragment<SelectionContract.View, SelectionPresenter>(),SelectionContract.View, SwipeRefreshLayout.OnRefreshListener{

    companion object {
        fun newInstance(): SectionFragment {
            return SectionFragment()
        }
    }

    private val mHeaderList = ArrayList<SelectionBean.Head>()      //顶部banner
    private val mSpecialList = ArrayList<SelectionBean.Zhuanlan>() //名师推荐
    private val mSerialList = ArrayList<SelectionBean.Serial>()    //系列好课
    private val mVideoList = ArrayList<SelectionBean.Video>()      //好课抢先看
    private val mCourseList = ArrayList<SelectionBean.Course>()    //精选好课
    private var mSectionedAdapter: SectionedRVAdapter? = null

    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_section
    }

    @SuppressLint("WrongConstant")
    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        //初始化刷新控件
        refresh.run {
            setColorSchemeColors(VFrame.getColor(R.color.colorCourseMain))
            setOnRefreshListener(this@SectionFragment)
        }
        mSectionedAdapter = SectionedRVAdapter()
        val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recycler?.run {
            layoutManager = mLayoutManager
            adapter = mSectionedAdapter
        }
    }

    override fun lazyLoadData() {
        refresh.postDelayed({
            refresh.isRefreshing = true
            onRefresh()
        },300)

    }

    override fun onRefresh() {
        mPresenter.getSelection()
    }

    override fun createPresenter(): SelectionPresenter {
        return SelectionPresenter()
    }

    override fun showSelection(selectionBean: SelectionBean) {
        mHeaderList.addAll(selectionBean.head)
        mSpecialList.addAll(selectionBean.zhuanlan)
        mSerialList.addAll(selectionBean.serial)
        mVideoList.addAll(selectionBean.video)
        mCourseList.addAll(selectionBean.course)
        finishTask()
    }

    private fun finishTask() {
        refresh.run {
            isRefreshing = false //加载更多时不能同时下拉刷新
        }
        if (mHeaderList.size != 0) mSectionedAdapter?.addSection(SectionBanner(mHeaderList))
        if (mSpecialList.size != 0) mSectionedAdapter?.addSection(SectionSpecial(this@SectionFragment,mSpecialList))
        if (mSerialList.size != 0) mSectionedAdapter?.addSection(SectionSerial(mSerialList))
        if (mCourseList.size != 0) mSectionedAdapter?.addSection(SectionCourse(mCourseList))
        mSectionedAdapter?.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        mHeaderList.clear()
        mSpecialList.clear()
        mSerialList.clear()
        mVideoList.clear()
        mCourseList.clear()
        mSectionedAdapter?.removeAllSections()
    }
}