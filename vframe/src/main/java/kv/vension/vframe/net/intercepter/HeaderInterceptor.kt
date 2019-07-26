package kv.vension.vframe.net.intercepter

import com.leifu.mvpkotlin.util.PreferenceUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/10/31 15:11
 * 描  述：请求头拦截器
 * ========================================================
 */

class HeaderInterceptor : Interceptor {

     val COOKIE_NAME = "Cookie"
     val COLLECTIONS_WEBSITE = "lg/collect"
     val UNCOLLECTIONS_WEBSITE = "lg/uncollect"
     val ARTICLE_WEBSITE = "article"
     val TODO_WEBSITE = "lg/todo"

    /**
     * token
     */
//    private var token: String by PreferenceUtil(Constant.TOKEN_KEY, "")

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()

        builder.addHeader("Content-type", "application/json; charset=utf-8")
                // .header("token", token)
                // .method(originalRequest.method(), request.body())
//            .header ("Connection", "keep-alive")
//            .header ("Accept", "*/*")
//            .header ("Cache-Control", String.format("public, max-age=%d", 60))
//                    .header ("Authorization", _Authorization)
//                    .header ("X-Oc-TimeStamp", _TimeStamp)
//            .header ("X-Oc-Device-Model", android.os.Build.MODEL)
//            .header ("X-Oc-Os-Model", "Android " + android.os.Build.VERSION.RELEASE)
//            .header ("X-Oc-App-Bundle", AppUtil.getAppVersionCode(VFrame.getContext()).toString())
//            .header ("X-Oc-App-Version", AppUtil.getAppVersionName(VFrame.getContext()))
//            .header ("X-Oc-Merchant-Language", "2")
//                    .header ("X-Oc-Sign", MD5Helper.MD5(_SignedString).toString().toLowerCase())
//            .method(originalRequest.method(), originalRequest.body())
        val domain = originalRequest.url().host()
        val url = originalRequest.url().toString()
        if (domain.isNotEmpty() && (url.contains(COLLECTIONS_WEBSITE)
                        || url.contains(UNCOLLECTIONS_WEBSITE)
                        || url.contains(ARTICLE_WEBSITE)
                        || url.contains(TODO_WEBSITE))) {
            val spDomain: String by PreferenceUtil(domain, "")
            val cookie: String = if (spDomain.isNotEmpty()) spDomain else ""
            if (cookie.isNotEmpty()) {
                // 将 Cookie 添加到请求头
                builder.addHeader(COOKIE_NAME, cookie)
            }
        }

        return chain.proceed(builder.build())
    }



}