package com.vension.fastframe.app.api

import io.reactivex.Flowable
import io.reactivex.Observable
import com.vension.fastframe.app.bean.ObjectBean
import com.vension.fastframe.app.bean.TBean
import kv.vension.fastframe.net.response.BaseBean
import kv.vension.fastframe.net.response.BaseResponseBean
import retrofit2.http.GET

/**
 *创建人:雷富
 *创建时间:2019/6/5 17:16
 *描述:
 */
interface ApiService {
    // https://www.apiopen.top/femaleNameApi?page=1
    //https://www.apiopen.top/createUserKey?appId=com.chat.peakchao&passwd=123456
    /**
     * 创建应用接口
     */
    @GET("createUserKey?appId=com.chat.peakchao&passwd=123456")
    fun getObservableObjectData(): Observable<ObjectBean>

    /**
     * 创建应用接口
     */
    @GET("createUserKey?appId=com.chat.peakchao&passwd=123456")
    fun getFlowableObjectData(): Flowable<ObjectBean>

    /**
     * 创建应用接口
     */
    @GET("createUserKey?appId=com.chat.peakchao&passwd=123456")
    fun getTData(): Flowable<BaseResponseBean<TBean>>


    /**
     * 创建应用接口
     */
    @GET("createUserKey?appId=com.chat.peakchao&passwd=123456")
    fun getListData(): Observable<BaseBean>
}