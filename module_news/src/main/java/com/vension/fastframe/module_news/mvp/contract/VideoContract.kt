package com.vension.fastframe.module_news.mvp.contract

import com.vension.fastframe.module_news.bean.VideoNewsModel
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

interface VideoContract {

    interface View : IView {
        fun setVideoData(data: VideoNewsModel?)
        fun setVideoMoreWares(data: VideoNewsModel)
    }

    interface Presenter : IPresenter<View> {
        fun getVideoData(flag: Int, videoCurrentType: Int)
        fun getVideoMoreWares(videoCurrentType: Int)
    }

}