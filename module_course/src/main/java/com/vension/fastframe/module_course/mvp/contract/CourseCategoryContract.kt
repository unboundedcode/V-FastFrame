package com.vension.fastframe.module_course.mvp.contract

import com.vension.fastframe.module_course.bean.VideoBean
import com.vension.fastframe.module_course.bean.VideoGroup
import kv.vension.vframe.core.mvp.IPresenter
import kv.vension.vframe.core.mvp.IViewRefresh


/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/25 15:30
 * 更 新：2019/7/25 15:30
 * 描 述：
 * ========================================================
 */

interface CourseCategoryContract {

    interface View :IViewRefresh<VideoBean>{
        fun showVideo(videoGroup: VideoGroup)
    }

    interface Presenter : IPresenter<View> {
        fun getVideo()
    }

}