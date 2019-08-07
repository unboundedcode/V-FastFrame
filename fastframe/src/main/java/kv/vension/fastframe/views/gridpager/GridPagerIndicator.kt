package kv.vension.fastframe.views.gridpager

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import kv.vension.fastframe.R
import kv.vension.fastframe.utils.DensityUtil
import java.security.InvalidParameterException
import kotlin.math.min

/**
 * ===================================================================
 * @author: Created by Vension on 2019/8/6 14:42.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/8/6 14:42
 * @desc:   GridPager 指示器
 * ===================================================================
 */
class GridPagerIndicator : RadioGroup {

    companion object {
        private val tag = GridPagerIndicator::class.java.simpleName
        //默认的子控件的大小，单位是dp
        private val DEAFULT_CHILD_SIZE = 8
        //默认情况下子控件之间的间距，单位是dp
        private val DEFAULT_CHILD_MARGIN = 8
        //默认显示的颜色值
        private val DEFAULT_NORMAL_COLOR = Color.GRAY
        //默认选中显示的颜色值
        private val DEFAULT_SELECT_COLOR = Color.RED
    }

    //子控件显示的宽度
    private var mChildWidth = 0
    //子控件显示的高度
    private var mChildHeight = 0
    //两个子控件之间的间距
    private var mChildMargin = 0
    //正常情况下显示的颜色
    private var mNormalColor = DEFAULT_NORMAL_COLOR
    //选中的时候现实的颜色
    private var mSelectColor = DEFAULT_SELECT_COLOR
    //正常情况下显示的drawable
    private var mNormalDrawable: Drawable? = null
    //选中情况下显示的drawable
    private var mSelectDrawable: Drawable? = null
    //是否显示圆点
    private var mIsCircle = true
    //
    private var indicatorCheckedChangeListener: IndicatorCheckedChangeListener? = null


    constructor(context: Context?) : super(context){
        orientation = LinearLayout.HORIZONTAL
        mChildHeight = DensityUtil.dp2px(DEAFULT_CHILD_SIZE)
        mChildWidth = DensityUtil.dp2px(DEAFULT_CHILD_SIZE)
        mChildMargin = DensityUtil.dp2px( DEFAULT_CHILD_MARGIN)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        orientation = LinearLayout.HORIZONTAL
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.GridPagerIndicator)
        mChildWidth = attributes.getDimensionPixelSize(R.styleable.GridPagerIndicator_kv_child_width, DensityUtil.dp2px(DEAFULT_CHILD_SIZE))
        mChildHeight = attributes.getDimensionPixelSize(R.styleable.GridPagerIndicator_kv_child_height, DensityUtil.dp2px(DEAFULT_CHILD_SIZE))
        mChildMargin = attributes.getDimensionPixelSize(R.styleable.GridPagerIndicator_kv_child_margin, DensityUtil.dp2px(DEFAULT_CHILD_MARGIN))
        mIsCircle = attributes.getBoolean(R.styleable.GridPagerIndicator_kv_is_circle, true)
        mNormalColor = attributes.getColor(R.styleable.GridPagerIndicator_kv_normal_color, DEFAULT_NORMAL_COLOR)
        mSelectColor = attributes.getColor(R.styleable.GridPagerIndicator_kv_select_color, DEFAULT_SELECT_COLOR)
        attributes.recycle()
    }

    private fun getSpecialDrawable(isNormal: Boolean): Drawable {
        if (mIsCircle) {
            val width = min(mChildHeight, mChildWidth)
            val create = RoundedBitmapDrawableFactory.create(resources, getCircleDrawableBitmap(isNormal, width))
            create.isCircular = true
            return create
        } else {
            val drawable = ColorDrawable(if (isNormal) mNormalColor else mSelectColor)
            drawable.setBounds(0, 0, mChildWidth, mChildHeight)
            return drawable
        }
    }

    private fun getCircleDrawableBitmap(isNormal: Boolean, width: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        canvas.drawColor(if (isNormal) mNormalColor else mSelectColor)
        return bitmap
    }

    /**
     * 设置宽度
     *
     * @param mChildWidth
     * @return
     */
    fun setChildWidth(mChildWidth: Int): GridPagerIndicator {
        this.mChildWidth = mChildWidth
        return this
    }

    fun setChildHeight(mChildHeight: Int): GridPagerIndicator {
        this.mChildHeight = mChildHeight
        return this
    }

    fun isCircle(mIsCircle: Boolean): GridPagerIndicator {
        this.mIsCircle = mIsCircle
        return this
    }

    fun setChildMargin(mChildMargin: Int): GridPagerIndicator {
        this.mChildMargin = mChildMargin
        return this
    }

    fun setNormalColor(mNormalColor: Int): GridPagerIndicator {
        this.mNormalColor = mNormalColor
        return this
    }

    fun setSelectColor(mSelectColor: Int): GridPagerIndicator {
        this.mSelectColor = mSelectColor
        return this
    }

    fun setIndicatorCheckedChangeListener(listener : IndicatorCheckedChangeListener): GridPagerIndicator {
        this.indicatorCheckedChangeListener = listener
        return this
    }

    /**
     * 添加子View
     *
     * @param count 添加的数量
     */
    fun addChild(count: Int) {
        if (count < 1) {
            throw InvalidParameterException("count must be biger than 0")
        }
        mNormalDrawable = getSpecialDrawable(true)
        mSelectDrawable = getSpecialDrawable(false)
        clear()
        for (index in 0 until count) {
            val button = RadioButton(context)
            button.id = index
            val drawable = StateListDrawable()
            drawable.addState(intArrayOf(android.R.attr.state_checked), mSelectDrawable)
            drawable.addState(intArrayOf(), mNormalDrawable)
            button.buttonDrawable = ColorDrawable(Color.TRANSPARENT)
            button.setBackgroundDrawable(drawable)
            val params = LayoutParams(mChildWidth, mChildHeight)
            if (index != 0) {
                params.leftMargin = mChildMargin
            }
            addView(button, params)
        }
        val button = getChildAt(0) as RadioButton
        button.isChecked = true
        setOnCheckedChangeListener { group, checkedId ->
            if (indicatorCheckedChangeListener != null) {
                indicatorCheckedChangeListener!!.checkedChange(checkedId)
            }
        }
    }

    /**
     * 设置选中的位置
     *
     * @param position
     */
    fun setSelectPosition(position: Int) {
        val button = getChildAt(position) as RadioButton
        button.isChecked = true
    }

    /**
     * 清除所有
     */
    fun clear() {
        removeAllViews()
    }

    interface IndicatorCheckedChangeListener {
        fun checkedChange(position: Int)
    }

}