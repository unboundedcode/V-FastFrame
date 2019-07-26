package kv.vension.vframe.net.intercepter

import com.leifu.mvpkotlin.util.PreferenceUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/10/31 15:16
 * 描  述：SaveCookieInterceptor: 保存 Cookie
 * ========================================================
 */

class SaveCookieInterceptor : Interceptor {

     val SAVE_USER_LOGIN_KEY = "user/login"
     val SAVE_USER_REGISTER_KEY = "user/register"
    val SET_COOKIE_KEY = "set-cookie"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val requestUrl = request.url().toString()
        val domain = request.url().host()
        // set-cookie maybe has multi, login to save cookie
        if ((requestUrl.contains(SAVE_USER_LOGIN_KEY)
                        || requestUrl.contains(SAVE_USER_REGISTER_KEY))
                && response.headers(SET_COOKIE_KEY).isNotEmpty()
        ) {
            val cookies = response.headers(SET_COOKIE_KEY)
            val cookie = encodeCookie(cookies)
            saveCookie(requestUrl, domain, cookie)
        }
        return response
    }


    private fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        cookies
            .map { cookie ->
                cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            }
            .forEach {
                it.filterNot { set.contains(it) }.forEach { set.add(it) }
            }
        val ite = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }
        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }
        return sb.toString()
    }

    private fun saveCookie(url: String?, domain: String?, cookies: String) {
        url ?: return
        var spUrl: String by PreferenceUtil(url, cookies)
        @Suppress("UNUSED_VALUE")
        spUrl = cookies
        domain ?: return
        var spDomain: String by PreferenceUtil(domain, cookies)
        @Suppress("UNUSED_VALUE")
        spDomain = cookies
    }


}