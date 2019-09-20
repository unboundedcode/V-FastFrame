package lib.vension.fastframe.common

import com.alibaba.android.arouter.launcher.ARouter
import kv.vension.fastframe.core.AbsApplication
import kv.vension.fastframe.core.IApplication
import lib.vension.fastframe.common.router.RouterConfig

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/22 16:45.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/22 16:45
 * @desc:   character determines attitude, attitude determines destiny
 * ===================================================================
 */
open class CommonApplication : AbsApplication() {

    override fun initComponents() {
        if (BuildConfig.DEBUG) {//debug模式下初始化
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早,推荐在Application中初始化
        modulesApplicationInit()
    }


    private fun modulesApplicationInit() {
        for (moduleImpl in RouterConfig.MODULE_APPLICATION) {
            try {
                val clazz = Class.forName(moduleImpl)
                val app = clazz.newInstance()
                if (app is IApplication) {
                    app.initConfig()
                }
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            }

        }
    }

}