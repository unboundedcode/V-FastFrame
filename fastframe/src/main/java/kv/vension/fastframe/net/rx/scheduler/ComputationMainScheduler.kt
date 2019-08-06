package kv.vension.fastframe.net.rx.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/24 16:29
 * 描  述：
 * ========================================================
 */


class ComputationMainScheduler<T> private constructor() : BaseScheduler<T>(Schedulers.computation(), AndroidSchedulers.mainThread())
