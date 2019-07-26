package kv.vension.vframe.core.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/10/31 14:11
 * 描  述：该类内的每一个生成的 Fragment 都将保存在内存之中，
 *        因此适用于那些相对静态的页，数量也比较少的那种；
 *        如果需要处理有很多页，并且数据动态性较大、占用内存较多的情况，
 *        应该使用FragmentStatePagerAdapter。
 * ========================================================
 */

class BaseFragmentAdapter(fm: FragmentManager, private var fragmentList: List<Fragment>, private var titleList: List<String>) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titleList[position]
    }


    //设置fragment
    private fun setFragments(fm: FragmentManager, fragments: List<Fragment>, mTitles: List<String>) {
        val ft = fm.beginTransaction()
        for (f in this.fragmentList) {
            ft.remove(f)
        }
        ft.commitAllowingStateLoss()
        fm.executePendingTransactions()
        this.fragmentList = fragments
        this.titleList = mTitles
        notifyDataSetChanged()
    }


}