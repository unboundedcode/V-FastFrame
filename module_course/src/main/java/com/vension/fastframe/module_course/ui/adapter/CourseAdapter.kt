package com.vension.fastframe.module_course.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.vension.fastframe.module_course.R
import com.vension.fastframe.module_course.ui.fragment.CourseCategoryFragment
import kv.vension.fastframe.VFrame

/**
 * @author: ym  作者 E-mail: 15622113269@163.com
 * date: 2018/11/30
 * desc: 我的课程Tag
 *
 */
class CourseAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var mTitles = VFrame.getStringArray(R.array.course_title)
    private val mFragment = arrayOfNulls<Fragment>(mTitles.size)


    override fun getItem(position: Int): Fragment {
        if (mFragment[position] == null) {
            when (position) {
                0 ->
                    //全部课
                    mFragment[position] = CourseCategoryFragment.newInstance(1)
                1 ->
                    //付费课
                    mFragment[position] = CourseCategoryFragment.newInstance(2)
                2 ->
                    //公开课
                    mFragment[position] = CourseCategoryFragment.newInstance(3)
                3 ->
                    //过期课
                    mFragment[position] = CourseCategoryFragment.newInstance(4)
            }
        }
        return mFragment[position]!!
    }

    override fun getCount(): Int = mTitles.size

    override fun getPageTitle(position: Int): CharSequence = mTitles[position]
}