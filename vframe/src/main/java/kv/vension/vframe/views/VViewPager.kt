package kv.vension.vframe.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager

/**
 * ========================================================
 * @author: Created by Vension on 2018/10/31 09:33.
 * @email:  2506856664@qq.com
 * @desc:   与ScollerView嵌套使用时不丢失高度
 * ========================================================
 */
class VViewPager : ViewPager {

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet):  super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec: Int
        var height = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
            val h = child.measuredHeight
            if (h > height) {
                height = h
            }
        }
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

}