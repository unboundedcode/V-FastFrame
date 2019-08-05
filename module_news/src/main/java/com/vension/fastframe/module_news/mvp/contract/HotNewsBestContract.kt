package com.vension.fastframe.module_news.mvp.contract

import com.vension.fastframe.module_news.bean.HotNewsBestModel
import kv.vension.vframe.core.mvp.IPresenter
import kv.vension.vframe.core.mvp.IViewRefresh

/**
 * ===================================================================
 * @author: Created by Vension on 2019/8/5 10:24.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/8/5 10:24
 * @desc:热闻精选业务
 * ===================================================================
 */
interface HotNewsBestContract {

    interface View : IViewRefresh<HotNewsBestModel.Articles> {
        fun setHotNewsBestData(hotNewsBestModel: HotNewsBestModel?)
    }

    interface Presenter : IPresenter<View> {
        fun getHotNewsBestData()
    }

}