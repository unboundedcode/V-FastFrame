package kv.vension.vframe.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.GridView

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/18 18:03
 * 描  述：Gridview嵌套在SrcollView中会出现显示不全的情况
 *        这个类通过设置不滚动来避免
 * ========================================================
 */
class VGridView : GridView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet):super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2, View.MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
    }

}