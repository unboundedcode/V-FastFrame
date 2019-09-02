package com.vension.fastframe.app.ui.bus.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * ========================================================================
 * 作 者：Vension
 * 主 页：Github: https://github.com/Vension
 * 日 期：2019/9/2 11:13
 *   ______       _____ _______ /\/|_____ _____            __  __ ______
 *  |  ____/\    / ____|__   __|/\/  ____|  __ \     /\   |  \/  |  ____|
 *  | |__ /  \  | (___    | |     | |__  | |__) |   /  \  | \  / | |__
 *  |  __/ /\ \  \___ \   | |     |  __| |  _  /   / /\ \ | |\/| |  __|
 *  | | / ____ \ ____) |  | |     | |    | | \ \  / ____ \| |  | | |____
 *  |_|/_/    \_\_____/   |_|     |_|    |_|  \_\/_/    \_\_|  |_|______|
 *
 * Take advantage of youth and toss about !
 * ---------事件线程
 * ========================================================================
 */
enum class ThreadMode {
    MAIN_THREAD,
    NEW_THREAD,
    IO,
    SINGLE,
    COMPUTATION,
    TRAMPOLINE;

    companion object {

        fun getScheduler(thread: ThreadMode): Scheduler {
            val scheduler: Scheduler
            when (thread) {
                MAIN_THREAD -> scheduler = AndroidSchedulers.mainThread()
                NEW_THREAD -> scheduler = Schedulers.newThread()
                IO -> scheduler = Schedulers.io()
                SINGLE -> scheduler = Schedulers.single()
                COMPUTATION -> scheduler = Schedulers.computation()
                TRAMPOLINE -> scheduler = Schedulers.trampoline()
                else -> scheduler = AndroidSchedulers.mainThread()
            }
            return scheduler
        }
    }

}
