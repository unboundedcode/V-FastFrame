package kv.vension.vframe.cache

import android.app.Activity
import androidx.fragment.app.Fragment
import java.util.*
import kotlin.system.exitProcess

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/15 10:20.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/15 10:20
 * @desc:   页面堆栈缓存
 * ===================================================================
 */
open class PageCache<T : Any> internal constructor(){

    private val pageStack = Stack<T>()

    val pageList: List<T>
        @Synchronized get() = pageStack


    /**
     * 获取当前页面（堆栈中最后一个压入的）
     */
    val lastPage: T
        @Synchronized get() = pageStack.lastElement()


    @Synchronized
    fun add(t: T): Boolean {
        return !pageStack.contains(t) && pageStack.add(t)
    }

    @Synchronized
    fun remove(t: T): Boolean {
        return pageStack.contains(t) && pageStack.remove(t)
    }

    @Synchronized
    fun remove(position: Int): Boolean {
        return pageStack.removeAt(position) != null
    }

    @Synchronized
    fun getCurrentPage(_Class: Class<out T>): T? {
        for (t in pageStack) {
            if (t.javaClass.name == _Class.name) {
                return t
            }
        }
        return null
    }

    @Synchronized
    fun clear() {
        if (!pageStack.isEmpty()) {
            pageStack.clear()
        }
    }

    @Synchronized
    fun size(): Int {
        return pageStack.size
    }

    companion object{
        //加锁懒加载校验
        val pageFragmentCache:FragmentACache by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            FragmentACache()
        }
        val pageActivityCache:ActivityACache by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityACache()
        }
    }


    class FragmentACache : PageCache<Fragment>()

    class ActivityACache : PageCache<Activity>() {

        /**程序退出 */
        @Synchronized
        fun quit() {
            for (mActivity in pageList) {
                if (!mActivity.isFinishing) {
                    mActivity.finish()
                }
            }
            super.clear()
            // 杀死进程
            android.os.Process.killProcess(android.os.Process.myPid())
            // 退出程序
            exitProcess(0)
            // 通知系统回收
            System.gc()
        }
    }


}