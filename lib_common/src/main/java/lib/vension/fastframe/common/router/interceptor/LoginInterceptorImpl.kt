package lib.vension.fastframe.common.router.interceptor

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import kv.vension.fastframe.ext.Logi
import kv.vension.fastframe.utils.SPUtil
import lib.vension.fastframe.common.Constant
import lib.vension.fastframe.common.router.RouterConfig

/**
 * ========================================================================
 * @author: Created by Vension on 2019/9/20 14:19.
 * @email : vensionHu@qq.com
 * @Github: https://github.com/Vension
 * __      ________ _   _  _____ _____ ____  _   _
 * \ \    / /  ____| \ | |/ ____|_   _/ __ \| \ | |
 *  \ \  / /| |__  |  \| | (___   | || |  | |  \| |
 *   \ \/ / |  __| | . ` |\___ \  | || |  | | . ` |
 *    \  /  | |____| |\  |____) |_| || |__| | |\  |
 *     \/   |______|_| \_|_____/|_____\____/|_| \_|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * @desc: happy code -> ARouter 登录拦截器
 * ========================================================================
 */
@Interceptor(name = "login",priority = 6)
class LoginInterceptorImpl : IInterceptor{

    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        val path = postcard!!.path
        Logi("path->$path")
        val isLogin = SPUtil.getBoolean(Constant.IS_LOGIN, false)
        if(isLogin){// 如果已经登录不拦截
            callback!!.onContinue(postcard)
        }else{// 如果没有登录
            when(path){
                RouterConfig.PATH_COMMON_LOGINACTIVITY,
                RouterConfig.PATH_COMMON_SPLASHACTIVITY,
                RouterConfig.PATH_VIEW_MAINACTIVITY,
                RouterConfig.PATH_APP_MAINACTIVITY -> {
                    // 不需要登录的直接进入这个页面
                    callback!!.onContinue(postcard)
                }
                else -> {// 需要登录的直接拦截下来
                    callback!!.onInterrupt(null)
                }
            }
        }
    }

    override fun init(context: Context?) {
        Logi("路由登录拦截器初始化成功")//只会走一次
    }

}