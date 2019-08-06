package kv.vension.fastframe.core.mvp

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/2 14:46
 * 描  述：MVP-Presenter-接口
 * ========================================================
 */

interface IPresenter<in V : IView> {

    /**
     *  绑定 View
     */
    fun attachView(iView: V)

    /**
     * 解绑 View
     * 在框架中 {@link Activity#onDestroy()} 时会默认调用 {@link IPresenter#detachView()}
     */
    fun detachView()

}