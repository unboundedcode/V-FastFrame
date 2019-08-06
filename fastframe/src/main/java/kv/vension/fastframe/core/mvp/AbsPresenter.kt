package kv.vension.fastframe.core.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/10/29 12:28
 * 描  述：MVP-Presenter - 基类
 * ========================================================
 */

abstract class AbsPresenter<V : IView> : IPresenter<V>, LifecycleObserver {

    var mView: V? = null
        private set

    private var mCompositeDisposable: CompositeDisposable? = null


    override fun attachView(iView: V) {
        this.mView = iView
        if (mView is LifecycleOwner) {
            (mView as LifecycleOwner).lifecycle.addObserver(this)
        }
    }

    override fun detachView() {
        // 保证activity结束时取消所有正在执行的订阅
        mView = null
        mCompositeDisposable?.run {
            if (!isDisposed) {
                clear()// 保证Activity结束时取消
                mCompositeDisposable = null
            }
        }
    }

     fun addSubscription(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        disposable?.let { mCompositeDisposable?.add(it) }
    }

     fun addDisposable(disposable: Disposable?) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        disposable?.let { mCompositeDisposable?.add(it) }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }


}