package com.vension.fastframe.app.bean

import kv.vension.fastframe.net.response.BaseBean

/**
 *创建人:雷富
 *创建时间:2019/6/11 18:47
 *描述:
 */
data class ObjectBean(val data: Data) : BaseBean() {
    data class Data(val appId: String, val appkey: String)
}