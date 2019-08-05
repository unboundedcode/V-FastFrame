package com.vension.fastframe.module_news

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function
import kv.vension.vframe.net.exception.ApiException
import kv.vension.vframe.net.rx.RxHandler

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/25 16:08.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/25 16:08
 * @desc:
 * ===================================================================
 */
object RxHandlerNews {

    /**
     *compose判断结果,统一返回结果处理,支持背压
     * @param <T>
     * @return
    </T> */
    fun <T> handleObservableNewsResult(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.flatMap(Function<T, Observable<T>> { bean ->
                if (bean != null) {
                    RxHandler.createObservable(bean)
                } else {
                    Observable.error(ApiException(404,""))
                }
            })
        }
    }

}