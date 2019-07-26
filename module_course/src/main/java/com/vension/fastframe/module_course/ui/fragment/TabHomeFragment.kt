package com.vension.fastframe.module_course.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.vension.fastframe.module_course.Constant
import com.vension.fastframe.module_course.R
import com.vension.fastframe.module_course.bean.TagBean
import kotlinx.android.synthetic.main.fragment_tab_home.*
import kv.vension.vframe.core.AbsCompatFragment
import kv.vension.vframe.core.adapter.BaseFragmentStatePagerAdapter

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/25 17:48.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/25 17:48
 * @desc:  首页
 * ===================================================================
 */
class TabHomeFragment : AbsCompatFragment() {

    companion object {
        fun newInstance(mSubTag: TagBean?, numbers: ArrayList<Int>?, title: String?): TabHomeFragment {
            val fragment = TabHomeFragment()
            val bundle = Bundle()
            bundle.putSerializable(Constant.EXTRA_DISCOVERY, mSubTag)
            bundle.putIntegerArrayList(Constant.EXTRA_NEXTLIST, numbers)
            bundle.putString(Constant.EXTRA_TITLE, title)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var mStagesType: TagBean
    private val mTitles = mutableListOf<String>()
    private val mFragments = mutableListOf<Fragment>()
    private var numbers = ArrayList<Int>()
    private var title: String? = null

    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_tab_home
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            mStagesType = it.getSerializable(Constant.EXTRA_DISCOVERY) as TagBean
            numbers = it.getIntegerArrayList(Constant.EXTRA_NEXTLIST)!!
            title = it.getString(Constant.EXTRA_TITLE)
        }
        toolbar_text.text = title
        mTitles.add("精选")
        mFragments.add(SectionFragment.newInstance())
        mStagesType.subTags.forEach { (_, tagId, tagName, _) ->
            mTitles.add(tagName)
            //其它标签Tag页面
            mFragments.add(SectionTypeFragment.newInstance(tagId))
        }

        toolbar_text.setOnClickListener {
            val mBundle = Bundle()
            mBundle.putIntegerArrayList("number",numbers)
            startProxyActivity(ChoosePhaseFragment::class.java,mBundle)
        }
        view_pager.offscreenPageLimit = mStagesType.subTags.size + 1
        view_pager.adapter = BaseFragmentStatePagerAdapter(childFragmentManager, mFragments,mTitles)
        discovery_tabs.setViewPager(view_pager)
        view_pager.currentItem = 0
    }

    override fun lazyLoadData() {
    }

}