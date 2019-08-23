package kv.vension.fastframe.core.mvp

import android.view.View

/**
 * ========================================================================
 * 作 者：Vension
 * 主 页：Github: https://github.com/Vension
 * 日 期：2019/8/23 14:21
 *   ______       _____ _______ /\/|_____ _____            __  __ ______
 *  |  ____/\    / ____|__   __|/\/  ____|  __ \     /\   |  \/  |  ____|
 *  | |__ /  \  | (___    | |     | |__  | |__) |   /  \  | \  / | |__
 *  |  __/ /\ \  \___ \   | |     |  __| |  _  /   / /\ \ | |\/| |  __|
 *  | | / ____ \ ____) |  | |     | |    | | \ \  / ____ \| |  | | |____
 *  |_|/_/    \_\_____/   |_|     |_|    |_|  \_\/_/    \_\_|  |_|______|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * 描述：MVP-View-列表页通用接口
 * ========================================================================
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