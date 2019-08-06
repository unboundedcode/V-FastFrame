package com.vension.fastframe.module_course.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vension.fastframe.module_course.Constant
import com.vension.fastframe.module_course.R
import com.vension.fastframe.module_course.bean.VerticalBean
import com.vension.fastframe.module_course.mvp.contract.SelectionTypeContract
import com.vension.fastframe.module_course.mvp.presenter.SelectionTypePresenter
import com.vension.fastframe.module_course.ui.adapter.section.SectionTypeBanner
import com.vension.fastframe.module_course.ui.adapter.section.SectionTypeColumn
import com.vension.fastframe.module_course.ui.adapter.section.SectionTypeSubTag
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
class SectionTypeFragment : AbsCompatMVPFragment<SelectionTypeContract.View, SelectionTypePresenter>(),SelectionTypeContract.View,
    SwipeRefreshLayout.OnRefreshListener {

    companion object {
        fun newInstance(tid: Int): SectionTypeFragment {
            val fragment = SectionTypeFragment()
            val bundle = Bundle()
            bundle.putInt(Constant.EXTRA_TID, tid)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var mTid: Int = 0
    private val mHeaderList = ArrayList<VerticalBean.Banner>()  //顶部Banner
    private val mSubTagList = ArrayList<VerticalBean.SubTag>()  //顶部tab列表
    private val mColumnList = ArrayList<VerticalBean.Column>()  //列表item内容
    private var mSectionedAdapter: SectionedRVAdapter? = null

    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_section_type
    }

    @SuppressLint("WrongConstant")
    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            mTid = it.getInt(Constant.EXTRA_TID)
        }
        //初始化刷新控件
        refresh.run {
            setColorSchemeColors(VFrame.getColor(R.color.colorCourseMain))
            setOnRefreshListener(this@SectionTypeFragment)
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
        if(mTid>=0){
            mPresenter.getVertical(mTid)
        }
    }

    override fun createPresenter(): SelectionTypePresenter {
        return SelectionTypePresenter()
    }

    override fun showVertical(verticalBean: VerticalBean) {
        mHeaderList.addAll(verticalBean.banner)
        mColumnList.addAll(verticalBean.column)
        mSubTagList.addAll(verticalBean.subTag)
        finishTask()
    }

    private fun finishTask() {
        refresh.run {
            isRefreshing = false //加载更多时不能同时下拉刷新
        }
        if (mHeaderList.size != 0) mSectionedAdapter?.addSection(SectionTypeBanner(mHeaderList))
        if (mSubTagList.size != 0) mSectionedAdapter?.addSection(SectionTypeSubTag(mSubTagList))
        mColumnList.forEach {
            mSectionedAdapter?.addSection(SectionTypeColumn(it.title, it.courseCards))
        }
        mSectionedAdapter?.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        mHeaderList.clear()
        mSubTagList.clear()
        mColumnList.clear()
        mSectionedAdapter?.removeAllSections()
    }
}