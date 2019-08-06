package kv.vension.fastframe.views

import android.content.Context
import android.util.AttributeSet
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

    constructor(context: Context, attrs: AttributeSet,defStyle: Int):super(context, attrs,defStyle)

    /**
     * 这两个参数指明控件可获得的空间以及关于这个空间描述的元数据.
     * @param widthMeasureSpec 此控件获得的宽度
     * @param heightMeasureSpec 此控件获得的高度
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        /**
         * >> 表示右移 ，如：int i=15; i>>2的结果是3，移出的部分将被抛弃。
         *转为二进制的形式可能更好理解，0000 1111(15)右移2位的结果是0000 0011(3)，0001 1010(18)右移3位的结果是0000 0011(3)。
         *
         * 最大模式（MeasureSpec.AT_MOST）
        这个也就是父组件，能够给出的最大的空间，当前组件的长或宽最大只能为这么大，当然也可以比这个小。

        MeasureSpec.AT_MOST是最大尺寸，当控件的layout_width或layout_height指定为WRAP_CONTENT时，
        控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可。
        因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。


        static int makeMeasureSpec(int size,int mode):根据提供的大小值和模式创建一个测量值(格式)
         */
        val expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2, MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
    }

}