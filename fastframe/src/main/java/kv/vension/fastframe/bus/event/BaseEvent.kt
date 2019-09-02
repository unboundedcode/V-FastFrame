package kv.vension.fastframe.bus.event

/**
 * 自定义eventbus发送实体
 */
class BaseEvent<T> {

    /**
     * -1:退出登录时通知Mainactivity finish
     * 0：登录成功   刷新所有主界面数据列表
     * 1：退出成功   。。。
     *
     *
     *
     *
     *
     *
     */
    var code: Int = 0
    var msg: String? = null
    var data: T? = null
    var isNetConnected: Boolean = true //网络是否连接
    var isCollect: Boolean = false //是否收藏
    var id: Int = 0

}
