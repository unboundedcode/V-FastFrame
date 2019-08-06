package kv.vension.fastframe.event

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
    var isCollect: Boolean = false
    var id: Int = 0
    var isIngored: Boolean = false


}
