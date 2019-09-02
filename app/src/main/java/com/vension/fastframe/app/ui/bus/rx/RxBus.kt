package com.vension.fastframe.app.ui.bus.rx


import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.ConcurrentHashMap

/**
 * ========================================================================
 * 作 者：Vension
 * 主 页：Github: https://github.com/Vension
 * 日 期：2019/9/2 11:14
 *   ______       _____ _______ /\/|_____ _____            __  __ ______
 *  |  ____/\    / ____|__   __|/\/  ____|  __ \     /\   |  \/  |  ____|
 *  | |__ /  \  | (___    | |     | |__  | |__) |   /  \  | \  / | |__
 *  |  __/ /\ \  \___ \   | |     |  __| |  _  /   / /\ \ | |\/| |  __|
 *  | | / ____ \ ____) |  | |     | |    | | \ \  / ____ \| |  | | |____
 *  |_|/_/    \_\_____/   |_|     |_|    |_|  \_\/_/    \_\_|  |_|______|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * 描述：RxBus事件管理
 * ========================================================================
 */
class RxBus : EventBase() {

    private val mEventCompositeMap = ConcurrentHashMap<Any, EventComposite>()

    /**
     * 注册事件监听
     *
     * @param subscribe
     */
    fun register(subscribe: Any?) {
        if (subscribe == null) {
            throw NullPointerException("Object to register must not be null.")
        }
        val compositeDisposable = CompositeDisposable()
        val subscriberMethods =
            EventFind.findAnnotatedSubscriberMethods(subscribe, compositeDisposable)
        mEventCompositeMap[subscribe] = subscriberMethods

        if (STICKY_EVENT_MAP.isNotEmpty()) {//如果有粘性事件，则发送粘性事件
            subscriberMethods.subscriberSticky(STICKY_EVENT_MAP)
        }
    }

    /**
     * 取消事件监听
     *
     * @param subscribe
     */
    fun unregister(subscribe: Any?) {
        if (subscribe == null) {
            throw NullPointerException("Object to register must not be null.")
        }
        val subscriberMethods = mEventCompositeMap[subscribe]
        subscriberMethods?.compositeDisposable?.dispose()
        mEventCompositeMap.remove(subscribe)
    }

    /**
     * 发送普通事件
     *
     * @param event
     */
    fun post(event: Any) {
        SUBJECT.onNext(event)
    }

    /**
     * 发送粘性事件
     *
     * @param event
     */
    fun postSticky(event: Any) {
        STICKY_EVENT_MAP[event.javaClass] = event
        post(event)
    }
}
