package com.vension.fastframe.module_news.mvp.contract

import com.vension.fastframe.module_news.bean.HomeTophotIndex
import kv.vension.vframe.core.mvp.IPresenter
import kv.vension.vframe.core.mvp.IViewRefresh

/**
 * ===================================================================
 * @author: Created by Vension on 2019/8/2 15:31.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/8/2 15:31
 * @desc:
 * ===================================================================
 */
interface HomeRecommendContract {

    interface View : IViewRefresh<HomeTophotIndex.Articles> {
        fun setHomeHotTopData(result : HomeTophotIndex)
    }

    interface Presenter : IPresenter<View> {
        fun getHomeHotTopData(flag: Int, videoCurrentType: Int)
    }

}