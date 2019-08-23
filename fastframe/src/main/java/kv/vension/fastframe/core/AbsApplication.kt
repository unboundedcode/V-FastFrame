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
 * ========================================================================
 * 作 者：Vension
 * 主 页：Github: https://github.com/Vension
 * 日 期：2019/7/15 10:08
 *   ______       _____ _______ /\/|_____ _____            __  __ ______
 *  |  ____/\    / ____|__   __|/\/  ____|  __ \     /\   |  \/  |  ____|
 *  | |__ /  \  | (___    | |     | |__  | |__) |   /  \  | \  / | |__
 *  |  __/ /\ \  \___ \   | |     |  __| |  _  /   / /\ \ | |\/| |  __|
 *  | | / ____ \ ____) |  | |     | |    | | \ \  / ____ \| |  | | |____
 *  |_|/_/    \_\_____/   |_|     |_|    |_|  \_\/_/    \_\_|  |_|______|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * 描述：
 * ========================================================================
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