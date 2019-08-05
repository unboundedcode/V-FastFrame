package com.vension.fastframe.module_news.mvp.contract

import com.vension.fastframe.module_news.bean.HomeTophotIndex
import kv.vension.vframe.core.mvp.IPresenter
import kv.vension.vframe.core.mvp.IView

/**
 * ===================================================================
 * @author: Created by Vension on 2019/8/1 11:45.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/8/1 11:45
 * @desc:
 * ===================================================================
 */
interface HomeHotTopContract {

    interface View:IView {
        fun setHomeHotTopData(tophotBean: HomeTophotIndex?)
        fun setHomeHotTopWares(tophotBean: HomeTophotIndex)
        fun setMoreHomeHotTopWares(tophotBean: HomeTophotIndex)
    }

    interface Presenter:IPresenter<View> {
        fun getHomeHotTopData(flag: Int, videoCurrentType: Int)
        fun getHomeHotTopWares(videoCurrentType: Int)
        fun getMoreHomeHotTopWares(videoCurrentType: Int)
    }

}