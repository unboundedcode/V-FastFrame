package kv.vension.fastframe.bus

/**
 * ========================================================================
 * @author: Created by Vension on 2019/8/30 15:12.
 * @email:  vensionHu@qq.com
 * @Github: https://github.com/Vension
 * __      ________ _   _  _____ _____ ____  _   _
 * \ \    / /  ____| \ | |/ ____|_   _/ __ \| \ | |
 *  \ \  / /| |__  |  \| | (___   | || |  | |  \| |
 *   \ \/ / |  __| | . ` |\___ \  | || |  | | . ` |
 *    \  /  | |____| |\  |____) |_| || |__| | |\  |
 *     \/   |______|_| \_|_____/|_____\____/|_| \_|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * @desc: happy code ->事件总线接口
 * ========================================================================
 */
interface IBus {

    /**
     * 注册
     */
    fun register(subscriber : Any)
    /**
     * 注销
     */
    fun unRegister(subscriber:Any)
    /**
     * 发送事件
     */
    fun postEvent(event:Any)
    /**
     * 发送粘性事件
     */
    fun postStickyEvent(event:Any)

}