package kv.vension.fastframe.net.intercepter

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/10/31 15:15
 * 描  述：头部公共参数拦截器
 * ========================================================
 */

class QueryParameterInterceptor : Interceptor {

    /**
     * 设置公共参数
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request: Request
        val modifiedUrl = originalRequest.url().newBuilder()
                // Provide your custom parameter here
                .addQueryParameter("phoneSystem", "")
                .addQueryParameter("phoneModel", "")
                .build()
        request = originalRequest.newBuilder().url(modifiedUrl).build()
        return chain.proceed(request)
    }

}