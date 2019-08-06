package kv.vension.fastframe.net.rx.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/5/24 16:30
 * 描  述：
 * ========================================================
 */

class TrampolineMainScheduler<T> private constructor() : BaseScheduler<T>(Schedulers.trampoline(), AndroidSchedulers.mainThread())
