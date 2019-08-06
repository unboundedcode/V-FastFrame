package kv.vension.fastframe.core

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.gw.swipeback.tools.WxSwipeBackActivityManager
import kv.vension.fastframe.VFrame
import kv.vension.fastframe.cache.PageCache
import kotlin.properties.Delegates

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/15 10:08.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/15 10:08
 * @desc:   character determines attitude, attitude determines destiny
 * ===================================================================
 */
abstract class AbsApplication : MultiDexApplication(){

    companion object{
        private val TAG = "AbsApplication"
        var appContext : Context by Delegates.notNull()
            private set
        lateinit var instance:Application
    }

    /**
     * 这个最先执行
     */
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        //MultiDex分包方法 必须最先初始化
        MultiDex.install(this)
    }

    /**
     * 程序启动的时候执行
     */
    override fun onCreate() {
        super.onCreate()
        instance = this
        appContext = applicationContext
        VFrame.init(this) //初始化Frame入口
        WxSwipeBackActivityManager.getInstance().init(this) //初始化侧滑返回
        initComponents()//初始化组件Application
    }

    /**
     * Module类的APP初始化
     */
    abstract fun initComponents()

    /**
     * 程序终止的时候执行
     */
    override fun onTerminate() {
        super.onTerminate()
        PageCache.pageActivityCache.quit()
    }


}