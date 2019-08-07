package kv.vension.fastframe.core.mvp

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/2 14:41
 * 描  述：MVP-View-接口
 *        为了满足部分人的诉求以及向下兼容, {@link IView} 中的部分方法使用 JAVA 1.8 的默认方法实现,
 *        这样实现类可以按实际需求选择是否实现某些方法,不实现则使用默认方法中的逻辑
 *
 * ========================================================
 */

interface IView {

    /**
     * 显示进度条
     */
    fun showProgressDialog()

    /**
     * 隐藏进度条
     */
    fun dismissProgressDialog()

    /**
     * 显示加载
     */
    fun showLoading()

    /**
     * 显示内容视图
     */
    fun showContent()


    /**
     * 显示空视图
     */
    fun showEmpty()

    /**
     * 无网络
     */
    fun showNoNetwork()

    /**
     * 显示错误视图
     */
    fun showError()


    /**
     * 显示提示
     * @param message 消息内容
     */
    fun showMessage(msg: String = "请求失败")


}