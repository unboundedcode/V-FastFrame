package kv.vension.vframe.views

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/25 15:24
 * 更 新：2019/7/25 15:24
 * 描 述：重写Viewpager解决点击tab去除滑动动画效果的问题
 * ========================================================
 */

class NoScrollViewPager(context: Context,attrs: AttributeSet) : ViewPager(context,attrs) {


    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(x, y)
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, smoothScroll)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, false)
    }

}