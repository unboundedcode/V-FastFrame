package com.vension.fastframe.module_course.api

import com.vension.fastframe.module_course.bean.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/25 11:58
 * 更 新：2019/7/25 11:58
 * 描 述：Api接口类
 * ========================================================
 */

interface ApiService {

    /**
     * 首页层次（学习阶段分层）
     */
    @GET("/course3/api/content/stages?api_ver=1.10&keyfrom=course.3.2.2.android&model=MI_6&mid=8.0.0&imei=866822031582307&vendor=xiaomi&screen=1080x1920&abtest=6&Mkt1st=xiaomi&Mkt=xiaomi&Pdt=mCourse.android")
    fun getDiscoveryComment(): Observable<HttpResult<DiscoveryCommentBean>>

    /**
     * 获取相应Tag页面的内容(tag由上面接口获取)
     */

    @GET("/course3/api/vertical2?api_ver=1.10&keyfrom=course.3.2.2.android&model=MI_6&mid=8.0.0&imei=866822031582307&vendor=xiaomi&screen=1080x1920&abtest=6&Mkt1st=xiaomi&Mkt=xiaomi&Pdt=mCourse.android")
    fun getVertical(@Query("tag") tag: Int): Observable<HttpResult<VerticalBean>>

    @JvmSuppressWildcards
    @GET("/course3/api/user/profile/tag/set?api_ver=1.10&keyfrom=course.3.2.2.android&model=MI_6&mid=8.0.0&imei=866822031582307&vendor=xiaomi&screen=1080x1920&abtest=6&Mkt1st=xiaomi&Mkt=xiaomi&Pdt=mCourse.android")
    fun setTag(@QueryMap tags: Map<String, Any>): Observable<HttpResult<Any>>

    /**
     * 获取精选内容
     */
    @GET("/course3/api/apphome?api_ver=1.10&keyfrom=course.3.2.2.android&model=MI_6&mid=8.0.0&imei=866822031582307&vendor=xiaomi&screen=1440x2560&abtest=6&Mkt1st=xiaomi&Mkt=xiaomi&Pdt=mCourse.android&popup=false")
    fun getSelection(): Observable<HttpResult<SelectionBean>>

    /**
     * 获取头像，名字
     */
    @GET("/api/user_status.jsonp?&keyfrom=course.3.2.4.android&model=MI_6&mid=8.0.0&imei=866822031582307&vendor=xiaomi&screen=1080x1920&abtest=6&Mkt1st=xiaomi&Mkt=xiaomi&Pdt=mCourse.android")
    fun getMine(): Observable<HttpResult<MineBean>>

    /**
     * 获取视频流
     */
    @GET("/course3/api/content/video?api_ver=2.0&rank=0&keyfrom=course.3.2.4.android&model=MI_6&mid=8.0.0&imei=866822031582307&vendor=xiaomi&screen=1080x1920&abtest=6&Mkt1st=xiaomi&Mkt=xiaomi&Pdt=mCourse.android")
    fun getVideo(): Observable<HttpResult<VideoGroup>>

}
