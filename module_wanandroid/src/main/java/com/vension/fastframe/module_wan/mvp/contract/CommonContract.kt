package com.vension.fastframe.module_wan.mvp.contract

import kv.vension.vframe.core.mvp.IPresenter
import kv.vension.vframe.core.mvp.IViewRefresh


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/6 14:06
 * 描  述：
 * ========================================================
 */

interface CommonContract {

    interface View<data> : IViewRefresh<data> {

        fun showCollectSuccess(success: Boolean)

        fun showCancelCollectSuccess(success: Boolean)
    }

    interface Presenter<data,in V : View<data>> : IPresenter<V> {

        fun addCollectArticle(id: Int)

        fun cancelCollectArticle(id: Int)

    }

}