package com.vension.fastframe.module_wan.mvp.contract

import com.vension.fastframe.module_wan.bean.CollectionArticle
import com.vension.fastframe.module_wan.bean.CollectionResponseBody
import kv.vension.fastframe.core.mvp.IPresenter
import kv.vension.fastframe.core.mvp.IViewRefresh

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/23 11:06.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
interface CollectContract {

    interface View : IViewRefresh<CollectionArticle> {

        fun setCollectList(page:Int,articles: CollectionResponseBody<CollectionArticle>)

        fun showRemoveCollectSuccess(success: Boolean)

    }

    interface Presenter : IPresenter<View> {

        fun getCollectList(page: Int)

        fun removeCollectArticle(id: Int, originId: Int)

    }

}