package lib.vension.fastframe.common

/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/17 19:51
 * 更 新：2019/7/17 19:51
 * 描 述：所有模块均依赖lib_common  所以路由跳转均写入本ARouterPath方便调用
 *        其中: 路由跳转命名统一用：path+模块名+Activity名
 * ========================================================
 */
object RouterConfig {

    val MODULE_APPLICATION = arrayOf(
        "com.vension.fastframe.module_wan.WanAndroidApplication",
        "com.vension.fastframe.module_course.CourseApplication",
        "com.vension.fastframe.module_news.NewsApplication"
    )

    /**
     * App 启动页
     */
    const val PATH_APP_SPLASHACTIVITY = "/app/SplashActivity"
    /**
     * App 主页
     */
    const val PATH_APP_MAINACTIVITY = "/app/AppMainActivity"


    /**
     * module_wanandroid 主页
     */
    const val PATH_MODULE_WANANDROID_MAINACTIVITY = "/module_wanandroid/WanMainActivity"

    /**
     * module_course
     */
    //微课主页
    const val PATH_MODULE_COURSE_MAINACTIVITY = "/module_course/CourseMainActivity"
    //微课视频播放页
    const val PATH_MODULE_COURSE_VIDEOPLAYACTIVITY = "/module_course/VideoPlayActivity"

    /**
     * module_news 主页
     */
    const val PATH_MODULE_NEWS_MAINACTIVITY = "/module_news/NewsMainActivity"


    /**
     * common
     */
    //登录 注册
    const val PATH_COMMON_LOGINACTIVITY = "/lib_common/LoginActivity"

}