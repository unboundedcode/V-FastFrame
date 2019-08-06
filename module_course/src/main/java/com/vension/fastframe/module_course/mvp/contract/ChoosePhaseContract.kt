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

interface ChoosePhaseContract {

    interface View : IView {

        fun showDiscoveryComment(mData: DiscoveryCommentBean)
    }

    interface Presenter : IPresenter<View> {

        fun getDiscoveryComment()
    }
}