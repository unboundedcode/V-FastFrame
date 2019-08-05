package com.vension.fastframe.module_news.api

import com.vension.fastframe.module_news.bean.NewsDetailModel
import com.vension.fastframe.module_news.bean.NewsMainModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/31 11:32.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/31 11:32
 * @desc:
 * ===================================================================
 */
interface NewsApiService {

    /*头条*/
    //http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
    @GET("nc/article/headline/{id}/{start}-{end}.html")
    fun getHeaderNews(@Path("id") id: String,
                               @Path("start") start: Int,
                               @Path("end") end: Int): Observable<NewsMainModel>

    /**
     * 新闻详情
     * @param articleUrl
     * @Url 它允许我们直接传入一个请求的URL。这样以来我们可以将上一个请求的获得的url直接传入进来，baseUrl将被无视
     * baseUrl 需要符合标准，为空、""、或不合法将会报错
     * @return
     */
    @GET
    fun getNewsDetail(@Url articleUrl: String): Observable<NewsDetailModel>

}