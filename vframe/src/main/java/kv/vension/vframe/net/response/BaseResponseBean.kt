package kv.vension.vframe.net.response

import com.google.gson.annotations.SerializedName

/**
 * 封装返回的数据
 *
 */
class BaseResponseBean<T>(
    @SerializedName("code")
    var code: Int,
    @SerializedName("msg")
    var msg: String,
    @SerializedName("errorCode")
    var errorCode: Int,
    @SerializedName("errorMsg")
    var errorMsg: String,
    var data: T
)