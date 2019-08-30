package kv.vension.fastframe.utils

import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/7 10:34
 * 描  述：软键盘管理工具类
 * ========================================================
 */

object KeyBoardUtil {


    /**
     * 是否落在 EditText 区域
     * 指定的View是否在触摸事件内
     */
    fun isHideKeyboard(view: View?, event: MotionEvent): Boolean {
        if (view != null && view is EditText) {
            val location = intArrayOf(0, 0)
            view.getLocationInWindow(location)
            //获取现在拥有焦点的控件view的位置，即EditText
            val left = location[0]
            val top = location[1]
            val bottom = top + view.width
            val right = left + view.height
            //判断我们手指点击的区域是否落在EditText上面，如果不是，则返回true，否则返回false
            val isInEt = (event.x > left && event.x < right && event.y > top && event.y < bottom)
            return !isInEt
        }
        return false
    }


    /**
     * 打卡软键盘
     */
    fun openKeyBoard(focusView: View, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(focusView, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyBoard(mContext: Context,focusView: View) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(focusView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }


    /**
     * 根据触摸事件判断释放应该收起键盘
     *
     * @param activity    页面
     * @param event       触摸事件
     * @param viewNotHide 触摸后不需收起键盘的view
     * @return
     */
    fun handleKeyboardHide(activity: Activity, event: MotionEvent, vararg viewNotHide: View): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            var viewCheck: View
            for (i in viewNotHide.indices) {
                viewCheck = viewNotHide[i]
                if (viewCheck.isClickable && isHideKeyboard(viewCheck, event)) {
                    return false
                }
            }

            val viewFocus = activity.currentFocus
            if (viewFocus != null && viewFocus is EditText) {
                if (isHideKeyboard(viewFocus, event)) {
                    return false
                } else {
                    closeKeyBoard(activity,viewFocus)
                    return true
                }
            }
        }
        return false
    }


}