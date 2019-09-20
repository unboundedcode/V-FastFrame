package lib.vension.fastframe.common.router.interceptor

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import kv.vension.fastframe.ext.Logi
import lib.vension.fastframe.common.router.RouterConfig

/**
 * ========================================================================
 * @author: Created by Vension on 2019/9/20 14:30.
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
 * @desc: happy code -> ARouter 登录拦截跳转回调
 *
 * 遇到的问题：
 * 1. **页面之间跳转使用阿里巴巴ARouter跳转，其中注意带返回值启动的时候请求吗不能为0否则没有回调onActivityResult**
 * 2. **使用ARouter传递参数的时候如果有传递Bundle跟其他的属性Bundle必须写在前面否则其他的属性注入不了**
 * ========================================================================
 */
class LoginNavigationCallbackImpl : NavigationCallback{

    override fun onLost(postcard: Postcard?) {
        //找不到了
        Logi("找不到了")
    }

    override fun onFound(postcard: Postcard?) {
        //找到了
        Logi("找到了")
    }

    override fun onInterrupt(postcard: Postcard?) {
        // 拦截了
        val path = postcard!!.path
        Logi("拦截了${path}")
        val bundle = postcard.extras
        ARouter.getInstance().build(RouterConfig.PATH_COMMON_LOGINACTIVITY)
            .with(bundle)
            .withString("targetUrl",path)
            .navigation()
    }

    override fun onArrival(postcard: Postcard?) {
        //跳转成功了
        Logi("跳转成功了")
    }

}