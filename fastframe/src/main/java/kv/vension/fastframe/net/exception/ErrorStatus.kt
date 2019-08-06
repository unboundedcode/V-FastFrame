package kv.vension.fastframe.net.exception

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/10/31 15:19
 * 描  述：Http状态码
 * ========================================================
 */

object ErrorStatus {
    /**
     * 响应成功
     */
    @JvmField
    val SUCCESS = 0

    /**
     * Token 过期
     */
    @JvmField
    val TOKEN_INVAILD = 401

    /**
     * 未知错误
     */
    @JvmField
    val UNKNOWN_ERROR = 1002

    /**
     * 服务器内部错误
     */
    @JvmField
    val SERVER_ERROR = 1003

    /**
     * 网络连接超时
     */
    @JvmField
    val NETWORK_ERROR = 1004

    /**
     * API解析异常（或者第三方数据结构更改）等其他异常
     */
    @JvmField
    val API_ERROR = 1005

}