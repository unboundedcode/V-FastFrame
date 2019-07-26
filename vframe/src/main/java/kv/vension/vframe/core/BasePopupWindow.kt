package kv.vension.vframe.core

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.widget.PopupWindow
import androidx.annotation.RequiresApi


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/7 12:05
 * 描  述：自定义PopWindow类，封装了PopWindow的一些常用属性，用Builder模式支持链式调用
 * ========================================================
 */

open class BasePopupWindow :PopupWindow.OnDismissListener {

    private val TAG = "BasePopupWindow"
    private val DEFAULT_ALPHA = 0.7f
    private var mContext: Context
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mIsFocusable = true
    private var mIsOutside = true
    private var mResLayoutId = -1
    private var mContentView: View? = null
    private var mPopupWindow: PopupWindow? = null
    private var mAnimationStyle = -1

    private var mClippEnable = true//default is true
    private var mIgnoreCheekPress = false
    private var mInputMode = -1
    private var mOnDismissListener: PopupWindow.OnDismissListener? = null
    private var mSoftInputMode = -1
    private var mTouchable = true//default is ture
    private var mOnTouchListener: View.OnTouchListener? = null

    private var mWindow: Window? = null//当前Activity 的窗口
    /**
     * 弹出PopWindow 背景是否变暗，默认不会变暗。
     */
    private var mIsBackgroundDark = false

    private var mBackgroundDrakValue = 0f// 背景变暗的值，0 - 1
    /**
     * 设置是否允许点击 PopupWindow之外的地方，关闭PopupWindow
     */
    private var enableOutsideTouchDisMiss = true// 默认点击pop之外的地方可以关闭

    constructor(context: Context){
        mContext = context
    }

    fun getWidth(): Int {
        return mWidth
    }

    fun getHeight(): Int {
        return mHeight
    }

    /**
     *
     * @param anchor
     * @param xOff
     * @param yOff
     * @return
     */
    fun showAsDropDown(anchor: View, xOff: Int, yOff: Int): BasePopupWindow {
        if (mPopupWindow != null) {
            mPopupWindow!!.showAsDropDown(anchor, xOff, yOff)
        }
        return this
    }

    fun showAsDropDown(anchor: View): BasePopupWindow {
        if (mPopupWindow != null) {
            mPopupWindow!!.showAsDropDown(anchor)
        }
        return this
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    fun showAsDropDown(anchor: View, xOff: Int, yOff: Int, gravity: Int): BasePopupWindow {
        if (mPopupWindow != null) {
            mPopupWindow!!.showAsDropDown(anchor, xOff, yOff, gravity)
        }
        return this
    }


    /**
     * 相对于父控件的位置（通过设置Gravity.CENTER，下方Gravity.BOTTOM等 ），可以设置具体位置坐标
     * @param parent 父控件
     * @param gravity
     * @param x the popup's x location offset
     * @param y the popup's y location offset
     * @return
     */
    fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int): BasePopupWindow {
        if (mPopupWindow != null) {
            mPopupWindow!!.showAtLocation(parent, gravity, x, y)
        }
        return this
    }

    /**
     * 添加一些属性设置
     * @param popupWindow
     */
    private fun apply(popupWindow: PopupWindow) {
        popupWindow.isClippingEnabled = mClippEnable
        if (mIgnoreCheekPress) {
            popupWindow.setIgnoreCheekPress()
        }
        if (mInputMode != -1) {
            popupWindow.inputMethodMode = mInputMode
        }
        if (mSoftInputMode != -1) {
            popupWindow.softInputMode = mSoftInputMode
        }
        if (mOnDismissListener != null) {
            popupWindow.setOnDismissListener(mOnDismissListener)
        }
        if (mOnTouchListener != null) {
            popupWindow.setTouchInterceptor(mOnTouchListener)
        }
        popupWindow.isTouchable = mTouchable


    }

    private fun build(): PopupWindow {

        if (mContentView == null) {
            mContentView = LayoutInflater.from(mContext).inflate(mResLayoutId, null)
        }

        // 2017.3.17 add
        // 获取当前Activity的window
        val activity = mContentView!!.context as Activity
        if (activity != null && mIsBackgroundDark) {
            //如果设置的值在0 - 1的范围内，则用设置的值，否则用默认值
            val alpha =
                if (mBackgroundDrakValue > 0 && mBackgroundDrakValue < 1) mBackgroundDrakValue else DEFAULT_ALPHA
            mWindow = activity.window
            val params = mWindow!!.attributes
            params.alpha = alpha
            mWindow!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            mWindow!!.attributes = params
        }


        if (mWidth != 0 && mHeight != 0) {
            mPopupWindow = PopupWindow(mContentView, mWidth, mHeight)
        } else {
            mPopupWindow =
                    PopupWindow(mContentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        if (mAnimationStyle != -1) {
            mPopupWindow!!.animationStyle = mAnimationStyle
        }

        apply(mPopupWindow!!)//设置一些属性

        if (mWidth == 0 || mHeight == 0) {
            mPopupWindow!!.contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            //如果外面没有设置宽高的情况下，计算宽高并赋值
            mWidth = mPopupWindow!!.contentView.measuredWidth
            mHeight = mPopupWindow!!.contentView.measuredHeight
        }

        // 添加dissmiss 监听
        mPopupWindow!!.setOnDismissListener(this)

        //2017.6.27 add:fix 设置  setOutsideTouchable（false）点击外部取消的bug.
        // 判断是否点击PopupWindow之外的地方关闭 popWindow
        if (!enableOutsideTouchDisMiss) {
            //注意这三个属性必须同时设置，不然不能disMiss，以下三行代码在Android 4.4 上是可以，然后在Android 6.0以上，下面的三行代码就不起作用了，就得用下面的方法
            mPopupWindow!!.isFocusable = true
            mPopupWindow!!.isOutsideTouchable = false
            mPopupWindow!!.setBackgroundDrawable(null)
            //注意下面这三个是contentView 不是PopupWindow
            mPopupWindow!!.contentView.isFocusable = true
            mPopupWindow!!.contentView.isFocusableInTouchMode = true
            mPopupWindow!!.contentView.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mPopupWindow!!.dismiss()

                    return@OnKeyListener true
                }
                false
            })
            //在Android 6.0以上 ，只能通过拦截事件来解决
            mPopupWindow!!.setTouchInterceptor(OnTouchListener { _, event ->
                val x = event.x.toInt()
                val y = event.y.toInt()

                if (event.action == MotionEvent.ACTION_DOWN && (x < 0 || x >= mWidth || y < 0 || y >= mHeight)) {
                    Log.e(TAG, "out side ")
                    Log.e(
                        TAG,
                        "width:" + mPopupWindow!!.width + "height:" + mPopupWindow!!.height + " x:" + x + " y  :" + y
                    )
                    return@OnTouchListener true
                } else if (event.action == MotionEvent.ACTION_OUTSIDE) {
                    Log.e(TAG, "out side ...")
                    return@OnTouchListener true
                }
                false
            })
        } else {
            mPopupWindow!!.isFocusable = mIsFocusable
            mPopupWindow!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mPopupWindow!!.isOutsideTouchable = mIsOutside
        }
        // update
        mPopupWindow!!.update()

        return mPopupWindow as PopupWindow
    }

    override fun onDismiss() {
        dissmiss()
    }

    /**
     * 关闭popWindow
     */
    fun dissmiss() {

        mOnDismissListener?.onDismiss()

        //如果设置了背景变暗，那么在dissmiss的时候需要还原
        if (mWindow != null) {
            val params = mWindow!!.getAttributes()
            params.alpha = 1.0f
            mWindow!!.setAttributes(params)
        }
        if (mPopupWindow != null && mPopupWindow!!.isShowing) {
            mPopupWindow!!.dismiss()
        }
    }

    fun getPopupWindow(): PopupWindow? {
        return mPopupWindow
    }

    class PopupWindowBuilder(context: Context) {
        private val mBasePopupWindow: BasePopupWindow = BasePopupWindow(context)

        fun size(width: Int, height: Int): PopupWindowBuilder {
            mBasePopupWindow.mWidth = width
            mBasePopupWindow.mHeight = height
            return this
        }


        fun setFocusable(focusable: Boolean): PopupWindowBuilder {
            mBasePopupWindow.mIsFocusable = focusable
            return this
        }


        fun setView(resLayoutId: Int): PopupWindowBuilder {
            mBasePopupWindow.mResLayoutId = resLayoutId
            mBasePopupWindow.mContentView = null
            return this
        }

        fun setView(view: View): PopupWindowBuilder {
            mBasePopupWindow.mContentView = view
            mBasePopupWindow.mResLayoutId = -1
            return this
        }

        fun setOutsideTouchable(outsideTouchable: Boolean): PopupWindowBuilder {
            mBasePopupWindow.mIsOutside = outsideTouchable
            return this
        }

        /**
         * 设置弹窗动画
         * @param animationStyle
         * @return
         */
        fun setAnimationStyle(animationStyle: Int): PopupWindowBuilder {
            mBasePopupWindow.mAnimationStyle = animationStyle
            return this
        }


        fun setClippingEnable(enable: Boolean): PopupWindowBuilder {
            mBasePopupWindow.mClippEnable = enable
            return this
        }


        fun setIgnoreCheekPress(ignoreCheekPress: Boolean): PopupWindowBuilder {
            mBasePopupWindow.mIgnoreCheekPress = ignoreCheekPress
            return this
        }

        fun setInputMethodMode(mode: Int): PopupWindowBuilder {
            mBasePopupWindow.mInputMode = mode
            return this
        }

        fun setOnDissmissListener(onDissmissListener: PopupWindow.OnDismissListener): PopupWindowBuilder {
            mBasePopupWindow.mOnDismissListener = onDissmissListener
            return this
        }


        fun setSoftInputMode(softInputMode: Int): PopupWindowBuilder {
            mBasePopupWindow.mSoftInputMode = softInputMode
            return this
        }


        fun setTouchable(touchable: Boolean): PopupWindowBuilder {
            mBasePopupWindow.mTouchable = touchable
            return this
        }

        fun setTouchIntercepter(touchIntercepter: View.OnTouchListener): PopupWindowBuilder {
            mBasePopupWindow.mOnTouchListener = touchIntercepter
            return this
        }

        /**
         * 设置背景变暗是否可用
         * @param isDark
         * @return
         */
        fun enableBackgroundDark(isDark: Boolean): PopupWindowBuilder {
            mBasePopupWindow.mIsBackgroundDark = isDark
            return this
        }

        /**
         * 设置背景变暗的值
         * @param darkValue
         * @return
         */
        fun setBgDarkAlpha(darkValue: Float): PopupWindowBuilder {
            mBasePopupWindow.mBackgroundDrakValue = darkValue
            return this
        }

        /**
         * 设置是否允许点击 PopupWindow之外的地方，关闭PopupWindow
         * @param disMiss
         * @return
         */
        fun enableOutsideTouchableDissmiss(disMiss: Boolean): PopupWindowBuilder {
            mBasePopupWindow.enableOutsideTouchDisMiss = disMiss
            return this
        }


        fun create(): BasePopupWindow {
            //构建PopWindow
            mBasePopupWindow.build()
            return mBasePopupWindow
        }

    }


}