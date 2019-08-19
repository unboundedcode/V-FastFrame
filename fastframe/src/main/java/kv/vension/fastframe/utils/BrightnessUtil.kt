package kv.vension.fastframe.utils

import android.app.Activity
import android.content.Context
import android.provider.Settings

/**
 * Introduction : 屏幕亮度工具类  brightness 0-255之间
 * 所需权限
 * <uses-permission android:name="android.permission.CHANGE_CONFIGURATION"></uses-permission>
 * <uses-permission android:name="android.permission.WRITE_SETTINGS"></uses-permission>
 */
object BrightnessUtil {

    /**
     * 判断是否开启了自动亮度调节
     */
    fun isAutoBrightness(context: Context): Boolean {
        val resolver = context.contentResolver
        var automicBrightness = false
        try {
            automicBrightness = Settings.System.getInt(
                resolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE
            ) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
        }

        return automicBrightness
    }

    /**
     * 获取当前屏幕亮度
     */
    fun getScreenBrightness(context: Context): Int {
        var nowBrightnessValue = 0
        val resolver = context.contentResolver
        try {
            nowBrightnessValue = Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return nowBrightnessValue
    }

    /**
     * 关闭自动亮度调节
     */
    fun autoBrightness(activity: Context, flag: Boolean): Boolean {
        var value = 0
        if (flag) {
            value = Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC //开启
        } else {
            value = Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL//关闭
        }
        return Settings.System.putInt(
            activity.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            value
        )
    }

    /**
     * 设置亮度，退出app也能保持该亮度值
     */
    fun saveBrightness(context: Context, brightness: Int) {
        val resolver = context.contentResolver
        val uri = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS)
        Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, brightness)
        resolver.notifyChange(uri, null)
    }

    /**
     * 设置当前activity显示的亮度
     */
    fun setBrightness(activity: Activity, brightness: Int) {
        val lp = activity.window.attributes
        lp.screenBrightness = java.lang.Float.valueOf(brightness.toFloat()) * (1f / 255f)
        activity.window.attributes = lp
    }
}
