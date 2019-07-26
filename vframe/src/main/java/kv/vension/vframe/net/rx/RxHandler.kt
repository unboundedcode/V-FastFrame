package kv.vension.vframe.net.rx


import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kv.vension.vframe.net.exception.ApiException
import kv.vension.vframe.net.exception.ErrorStatus
import kv.vension.vframe.net.function.RetryWithDelay
import kv.vension.vframe.net.response.BaseBean
import kv.vension.vframe.net.response.BaseResponseBean
import kv.vension.vframe.net.rx.scheduler.IoMainScheduler

/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/16 14:54
 * 更 新：2019/7/16 14:54
 * 描 述：rx统一线程处理
 *       虽然在Rxjava2中，Flowable是在Observable的基础上优化后的产物，Observable能解决的问题Flowable也都能解决，
 *       但是并不代表Flowable可以完全取代Observable,在使用的过程中，并不能抛弃Observable而只用Flowable。
 *       由于基于Flowable发射的数据流，以及对数据加工处理的各操作符都添加了背压支持，附加了额外的逻辑，其运行效率要比Observable慢得多。
 *       只有在需要处理背压问题时，才需要使用Flowable。
 * ========================================================
 */

object RxHandler {

    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }

    /**
     * compose简化线程切换至主线程
     * @param <T>
     * @return
    </T> */
    fun <T> rxSchedulerObservableToMain(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(RetryWithDelay())
        }
    }

    /**
     * compose简化线程切换至主线程
     * @param <T>
     * @return
    </T> */
    fun <T> rxSchedulerFlowableToMain(): FlowableTransformer<T, T> {
        return FlowableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     *compose判断结果,统一返回结果处理,支持背压
     * @param <T>
     * @return
    </T> */
    fun <T> handleObservableResult(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.flatMap(Function<T, Observable<T>> { bean ->
                val baseBean = bean as BaseBean
                if (baseBean.errorCode == ErrorStatus.SUCCESS || baseBean.code == 200) {//{"code":200,"msg":"成功!","data":{"appId":"com.chat.peakchao","appkey":"00d91e8e0cca2b76f515926a36db68f5"}}
                    createObservable(bean)
                } else {
                    Observable.error(ApiException(baseBean.errorCode,baseBean.errorMsg))
                }
            })
        }
    }

    /**
     *compose判断结果,统一返回结果处理,支持背压
     * @param <T>
     * @return
    </T> */
    fun <T> handleFlowableResult(): FlowableTransformer<T, T> {
        return FlowableTransformer { flowable ->
            flowable.flatMap(Function<T, Flowable<T>> { bean ->
                val baseBean = bean as BaseBean
                if (baseBean.errorCode == ErrorStatus.SUCCESS || baseBean.code == 200) {
                    createFlowable(bean)
                } else {
                    Flowable.error(ApiException(baseBean.code,baseBean.msg))
                }
            })
        }
    }

    /**
     *不通过继承,通过泛型实现实体类
     * @param <T>
     * @return
    </T> */
    fun <T> handleFlowableResult2(): FlowableTransformer<BaseResponseBean<T>, T> {
        return FlowableTransformer { flowable ->
            flowable.flatMap { bean ->
                if (bean.errorCode == ErrorStatus.SUCCESS || bean.code == 200) {
                    createFlowable(bean.data)
                } else {
                    Flowable.error(ApiException(bean.code,bean.msg))
                }
            }
        }
    }

    /**
     * 生成Observable,不支持背压
     * @param <T>
     * @return
    </T> */
     fun <T> createObservable(t: T): Observable<T> {
        return Observable.create { emitter ->
            try {
                emitter.onNext(t)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    /**
     * 生成Flowable,支持背压
     * @param <T>
     * @return
    </T> */
    private fun <T> createFlowable(t: T): Flowable<T> {
        return Flowable.create({ emitter ->
            try {
                emitter.onNext(t)
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }, BackpressureStrategy.BUFFER)
    }
}
