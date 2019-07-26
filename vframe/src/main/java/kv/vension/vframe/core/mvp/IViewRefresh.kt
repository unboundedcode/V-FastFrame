package kv.vension.vframe.core.mvp

import android.view.View

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/2 14:41
 * 描  述：MVP-View-列表页通用接口
 * ========================================================
 */

interface IViewRefresh<data> : IView {

    /**
     * 设置刷新数据
     */
    fun setRefreshData(listData: MutableList<data>)

    /**
     * 设置刷新数据
     */
    fun setRefreshData(listData: MutableList<data>, headers: List<View>?, footers: List<View>?)

    /**
     * 设置加载更多数据
     */
    fun setMoreData(listData: MutableList<data>)

    /**
     * 设置加载失败
     */
    fun loadFail()

}