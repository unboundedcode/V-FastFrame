package kv.vension.vframe.net.rx

import io.reactivex.subscribers.ResourceSubscriber
import kv.vension.vframe.core.mvp.IView
import kv.vension.vframe.net.exception.ErrorStatus
import kv.vension.vframe.net.exception.ExceptionHandle
import kv.vension.vframe.net.response.BaseBean
import kv.vension.vframe.utils.NetWorkUtil

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/6 14:24
 * 描  述：Rx2.x 订阅者
 * ========================================================
 */

abstract class AbsSubscriber<T : BaseBean>(view: IView? = null) : ResourceSubscriber<T>() {

    private var mView = view

    abstract fun onSuccess(t: T)

    override fun onComplete() {
        mView?.dismissProgressDialog()
    }

    override fun onStart() {
        super.onStart()
        mView?.showLoading()
        if (!NetWorkUtil.isNetworkAvailable()) {
            mView?.showMessage("网络连接不可用,请检查网络设置!")
            onComplete()
        }
    }

    override fun onNext(t: T) {
        when {
            t.code == ErrorStatus.SUCCESS -> onSuccess(t)
            t.code == ErrorStatus.TOKEN_INVAILD -> {
                // Token 过期，重新登录
            }
            else -> mView?.showMessage(t.msg)
        }
    }


    override fun onError(t: Throwable) {
        mView?.showError()
        mView?.dismissProgressDialog()
        ExceptionHandle.handleException(t,mView)
    }
}
