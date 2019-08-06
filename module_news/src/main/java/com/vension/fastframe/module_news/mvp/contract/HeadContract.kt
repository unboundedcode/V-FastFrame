package com.vension.fastframe.module_news.mvp.contract

import com.vension.fastframe.module_news.bean.FrontNewsModel
import com.vension.fastframe.module_news.bean.NewsMainModel
import kv.vension.fastframe.core.mvp.IPresenter
import kv.vension.fastframe.core.mvp.IView


/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/26 10:55
 * 更 新：2019/7/26 10:55
 * 描 述：主界面- MainContract
 * ========================================================
 */

interface HeadContract {

    interface View : IView {
        fun setNewChannels(channes: NewsMainModel)
        fun setFrontNewsData(data: FrontNewsModel)
        fun setFrontNewsMoreWares(data: FrontNewsModel)
    }

    interface Presenter : IPresenter<View> {
        fun getNewChannels()
        fun getFrontNewsData(flag: Int)
        fun getFrontNewsMoreWares()
    }

}