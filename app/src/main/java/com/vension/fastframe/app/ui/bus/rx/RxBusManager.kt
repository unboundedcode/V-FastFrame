package com.vension.fastframe.app.ui.bus.rx

import kv.vension.fastframe.bus.IBus
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ========================================================================
 * @author: Created by Vension on 2019/9/2 09:40.
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
 * @desc: happy code ->
 * ========================================================================
 */

@Singleton
class RxBusManager : IBus {

    private var mRxBus : RxBus = RxBus()

    @Inject
    constructor()


    override fun register(subscriber: Any) {
        mRxBus.register(subscriber)
    }

    override fun unRegister(subscriber: Any) {
        mRxBus.unregister(subscriber)
    }

    override fun postEvent(event: Any) {
        mRxBus.post(event)
    }

    override fun postStickyEvent(event: Any) {
        mRxBus.postSticky(event)
    }

}