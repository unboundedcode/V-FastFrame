package com.vension.fastframe.module_course.mvp.contract

import com.vension.fastframe.module_course.bean.DiscoveryCommentBean
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

interface MainContract {

    interface View : IView {

        fun showDiscoveryBean(mDiscoveryCommentBean: DiscoveryCommentBean)

        fun showSetTag()
    }

    interface Presenter : IPresenter<View> {

        /**
         * @param  tagId    [所处阶段Id,阶段对应级别（如初中->初一、初二、初三）]
         * @return
         */
        fun getRegionTagTypeBean(tagId: ArrayList<Int>)
    }
}