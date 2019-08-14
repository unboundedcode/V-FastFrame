package kv.vension.fastframe

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.LogcatLogStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import kv.vension.fastframe.cache.FileCache
import kv.vension.fastframe.utils.CrashUtil
import kv.vension.fastframe.utils.SharedPreferencesUtil

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/15 10:01.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/15 10:01
 * @desc:   character determines attitude, attitude determines destiny
 * ===================================================================
 */
@SuppressLint("StaticFieldLeak")
object VFrame {

    lateinit var mContext: Context
        private set
    lateinit var mApplication: Context
        private set


    fun init(application: Application) {
        mApplication = application
        mContext = application.applicationContext
        FileCache.init(application.applicationContext)
        initLogger()//初始化Logger日志打印
        initCrash()//崩溃日志收集
    }


    /**
     * 初始化Logger日志打印
     */
    private fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)                  // (可选）是否显示线程信息
            .methodCount(2)                         // (可选）要显示的方法行数
            .methodOffset(5)                        // (可选）设置调用堆栈的函数偏移值，0的话则从打印该Log函数开始输出堆栈信息，默认是0
            .logStrategy(LogcatLogStrategy())           // (可选）要改要打印的日志策略。默认LogCat
            .tag("V-FastFrame_Logger--->")         // (可选）每个日志的全局标记。默认PRETTY_LOGGER
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                //DEBUG模式下不打印LOG
                return BuildConfig.DEBUG
            }
        })
    }


    private fun initCrash() {
        CrashUtil.init()
    }

    fun getContext(): Context {
        return mContext
    }

    fun getApplication(): Context {
        return mApplication
    }


    /**
     * 获取资源对象
     * @return  Resources
     */
    fun getResources(): Resources {
        return getContext().resources
    }

    fun getString(@StringRes id: Int): String {
        return getResources().getString(id)
    }

    fun getTheme(): Resources.Theme {
        return getContext().theme
    }

    fun getAssets(): AssetManager {
        return getContext().assets
    }

    fun getDrawable(@DrawableRes id: Int): Drawable? {
        return ContextCompat.getDrawable(getContext(), id)
    }

    fun getColor(@ColorRes id: Int): Int {
        return ContextCompat.getColor(getContext(), id)
    }

    fun getSystemService(name: String): Any {
        return getContext().getSystemService(name)
    }

    fun getConfigguration(): Configuration {
        return getResources().configuration
    }

    fun getDisplayMetrics(): DisplayMetrics {
        return getResources().displayMetrics
    }

    fun getStringArray(@ArrayRes resId : Int): Array<out String> {
        return getResources().getStringArray(resId)
    }

    fun getColorStateList(stateColor: Int): ColorStateList? {
        //获取方法
        val states = intArrayOf(
            android.R.attr.state_enabled,
            -android.R.attr.state_enabled,
            -android.R.attr.state_checked,
            android.R.attr.state_pressed)
        val colors = intArrayOf(stateColor, stateColor, stateColor, stateColor)
        return ColorStateList(arrayOf(states), colors)
    }

    fun getPreferenceHelper(): SharedPreferencesUtil? {
       return SharedPreferencesUtil.getSingleInstance(getContext())
    }

}