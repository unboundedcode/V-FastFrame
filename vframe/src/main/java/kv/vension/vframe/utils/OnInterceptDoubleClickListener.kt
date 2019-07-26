package kv.vension.vframe.utils

import android.view.View


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/12/3 14:53
 * 描  述：防止连续点击
 * ========================================================
 */


abstract class OnInterceptDoubleClickListener : View.OnClickListener {

    private var mThrottleFirstTime: Long = 500
    private var mLastClickTime: Long = 0

    constructor()
    constructor(throttleFirstTime: Long) {
        mThrottleFirstTime = throttleFirstTime
    }

    override fun onClick(v: View?) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - mLastClickTime > mThrottleFirstTime) {
            mLastClickTime = currentTime
            onInterceptDoubleClick(v)
        }
    }

    /**拦截双击事件*/
    abstract fun onInterceptDoubleClick(v: View?)

}