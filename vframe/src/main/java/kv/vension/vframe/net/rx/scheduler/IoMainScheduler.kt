package kv.vension.vframe.net.rx.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/6 14:19
 * 描  述：
 * ========================================================
 */

class IoMainScheduler<T> : BaseScheduler<T>(Schedulers.io(), AndroidSchedulers.mainThread())
