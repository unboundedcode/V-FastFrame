package com.vension.fastframe.module_course.ui.fragment

import android.os.Bundle
import android.view.View
import com.vension.fastframe.module_course.R.layout
import com.vension.fastframe.module_course.ui.adapter.CourseAdapter
import kotlinx.android.synthetic.main.fragment_tab_course.*
import kv.vension.vframe.core.AbsCompatFragment

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/25 15:17.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/25 15:17
 * @desc:  我的课程
 * ===================================================================
 */
class TabCourseFragment : AbsCompatFragment() {

    companion object {
        fun newInstance(): TabCourseFragment {
            return TabCourseFragment()
        }
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return layout.fragment_tab_course
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        val adapter = CourseAdapter(childFragmentManager)
        view_pager.offscreenPageLimit = 3
        view_pager.adapter = adapter
        course_tabs.setViewPager(view_pager)
        view_pager.currentItem = 0
    }

    override fun lazyLoadData() {
    }

}