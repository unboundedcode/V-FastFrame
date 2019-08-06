package kv.vension.fastframe.net.response

import java.io.Serializable

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/26 9:23
 * 描  述：封装返回的数据 -> {"code":200,"msg":"成功!","data":{"appId":"kv.vension.vframe","appkey":"xxxxxxxxx"}}
 * ========================================================
 */
open class BaseBean : Serializable {
    var code: Int = 0
    var msg: String = ""
    var errorCode: Int = 0
    var errorMsg: String = ""
}
