package kv.vension.vframe.net.rx


import io.reactivex.subscribers.ResourceSubscriber
import kv.vension.vframe.core.mvp.IView
import kv.vension.vframe.net.exception.ExceptionHandle

/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/17 9:45
 * 更 新：2019/7/17 9:45
 * 描 述：
 * ========================================================
 */

abstract class FlowableSubscriberManager<T> : ResourceSubscriber<T> {
    var mView: IView? = null
    /**
     * 是否显示加载中
     */
    private var isShowLoading = true

    constructor(mView: IView?) {
        this.mView = mView
    }

    constructor(mView: IView?, isShowLoading: Boolean) {
        this.mView = mView
        this.isShowLoading = isShowLoading
    }


    override fun onStart() {
        super.onStart()
        if (isShowLoading) {
            mView?.showLoading()
        }
    }

    override fun onNext(t: T) {
        mView?.showContent()
    }

    override fun onComplete() {
        if (isShowLoading) {
            mView?.dismissProgressDialog()
        }
    }

    override fun onError(e: Throwable) {
        if (isShowLoading) {
            mView?.dismissProgressDialog()
        }
        ExceptionHandle.handleException(e, mView)
    }
}
