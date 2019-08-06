package kv.vension.fastframe.core.adapter

import android.text.Html
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/20 15:58.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
class BaseFragmentStatePagerAdapter(fm: FragmentManager, private var fragments: List<Fragment>, private var titles: List<String>) : FragmentStatePagerAdapter(fm) {


    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = Html.fromHtml(titles[position])

    override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE

    fun recreateItems(fragmentList: List<Fragment>, titleList: List<String>) {
        this.fragments = fragmentList
        this.titles = titleList
        notifyDataSetChanged()
    }


}