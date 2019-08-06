package kv.vension.fastframe.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import kv.vension.fastframe.core.AbsApplication


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/29 9:27
 * 描  述：尺寸工具类 - 涉及到屏幕宽度、高度、密度比、(像素、dp、sp)之间的转换等。
 * ========================================================
 */

object DensityUtil {

    /**
     * 获取屏幕宽度，单位为px
     */
    fun getScreenWidth(): Int {
        return AbsApplication.appContext.resources.displayMetrics.widthPixels
    }

    /**
     * 获取屏幕高度，单位为px
     */
    fun getScreenHeight(): Int {
        return AbsApplication.appContext.resources.displayMetrics.heightPixels
    }

    /**
     * 获取设备尺寸密度 -即系统dp尺寸密度值
     */
    fun getDensity() = AbsApplication.appContext.resources.displayMetrics.density

    /**
     * 获取设备收缩密度 - 即系统字体sp密度值
     */
    fun getScaleDensity() = AbsApplication.appContext.resources.displayMetrics.scaledDensity



    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    fun dp2px(context: Context, dpVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.resources.displayMetrics).toInt()
    }

    /**
     * sp转px
     * @param context
     * @param spVal
     * @return
     */
    fun sp2px(context: Context, spVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.resources.displayMetrics).toInt()
    }



    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return 转换后的px值
     */
    fun dp2px(dpValue: Int): Int {
        return (dpValue * getDensity() + 0.5f).toInt()
    }


    /**
     * px转换为dp值
     *
     * @param pxValue px值
     * @return 转换后的dp值
     */
    fun px2dp(pxValue: Int): Int {
        return (pxValue / getDensity() + 0.5f).toInt()
    }

    /**
     * sp转换为px
     *
     * @param spValue sp值
     * @return 转换后的px值
     */
    fun sp2px(spValue: Int): Int {
        return (spValue * getScaleDensity() + 0.5f).toInt()
    }

    /**
     * px转换为sp
     *
     * @param pxValue px值
     * @return 转换后的sp值
     */
    fun px2sp(pxValue: Int): Int {
        return (pxValue / getScaleDensity() + 0.5f).toInt()
    }



    /**
     * 获得状态栏的高度
     *
     * @return
     */
    fun getStatusBarHeight(): Int {
        var statusBarHeight = 0
        try {
            var c = Class.forName("com.android.internal.R\$dimen")
            var obj = c!!.newInstance()
            var field = c.getField("status_bar_height")
            val x1 = Integer.parseInt(field!!.get(obj).toString())
            statusBarHeight = AbsApplication.appContext.resources.getDimensionPixelSize(x1)
        } catch (var7: Exception) {
            var7.printStackTrace()
        }
        return statusBarHeight
    }

    /**
     * 获得屏幕的高度（不包括状态栏）
     *
     * @return
     */
    fun getAppInScreenheight(): Int {
        return getScreenHeight() - getStatusBarHeight()
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    fun snapShotWithStatusBar(activity: Activity): Bitmap {
        val decorView = activity.window.decorView
        decorView.isDrawingCacheEnabled = true
        decorView.buildDrawingCache()
        val bmp = decorView.drawingCache
        val width = getScreenWidth()
        val height = getScreenHeight()
        val bitmap: Bitmap
        bitmap = Bitmap.createBitmap(bmp, 0, 0, width, height)
        decorView.destroyDrawingCache()
        return bitmap
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    fun snapShotWithoutStatusBar(activity: Activity): Bitmap {
        val decorView = activity.window.decorView
        decorView.isDrawingCacheEnabled = true
        decorView.buildDrawingCache()
        val bmp = decorView.drawingCache
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusHeight = frame.top
        val width = getScreenWidth()
        val height = getScreenHeight()
        val bitmap: Bitmap
        bitmap = Bitmap.createBitmap(bmp, 0, statusHeight, width, height - statusHeight)
        decorView.destroyDrawingCache()
        return bitmap
    }

    /**
     * 获取DisplayMetrics对象
     *
     * @return
     */
    fun getDisplayMetrics(): DisplayMetrics {
        val manager = AbsApplication.appContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        manager.defaultDisplay.getMetrics(metrics)
        return metrics
    }

}