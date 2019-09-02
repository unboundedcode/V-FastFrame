package kv.vension.fastframe.bus

import org.greenrobot.eventbus.EventBus
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ========================================================================
 * @author: Created by Vension on 2019/8/30 16:02.
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
 * @desc: happy code -> EventBus管理者
 * IBus以外的方法，请通过{@link VFrame#busManager}来调用
 * ========================================================================
 */
@Singleton
class EventBusManager : IBus {

    @Inject
    constructor()

    /**
     * 订阅事件
     */
    override fun register(subscriber: Any) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber)
        }
    }

    /**
     * 取消订阅
     */
    override fun unRegister(subscriber: Any) {
        if (EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber)
        }
    }

    /**
     * 发送事件
     */
    override fun postEvent(event: Any) {
        EventBus.getDefault().post(event)
    }

    /**
     * 发送粘性事件
     */
    override fun postStickyEvent(event: Any) {
        EventBus.getDefault().postSticky(event)
    }

    /**
     * 终止事件继续传递
     */
    fun cancelDelivery(event: Any) {
        EventBus.getDefault().cancelEventDelivery(event)
    }

    /**
     * 获取保存起来的粘性事件
     */
    fun <T> getStickyEvent(classType: Class<T>): T {
        return EventBus.getDefault().getStickyEvent(classType)
    }

    /**
     * 删除保存中的粘性事件
     */
    fun removeStickyEvent(event: Any) {
        EventBus.getDefault().removeStickyEvent(event)
    }

}