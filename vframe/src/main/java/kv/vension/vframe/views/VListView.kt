package kv.vension.vframe.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ListView

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/18 18:03
 * 描  述：Listview嵌套在SrcollView中会出现显示不全的情况
 *        这个类通过设置不滚动来避免
 * ========================================================
 */
class VListView : ListView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet):super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2, MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
    }

}