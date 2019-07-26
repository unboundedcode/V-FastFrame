package com.vension.fastframe.module_course.mvp.contract

import com.vension.fastframe.module_course.bean.VerticalBean
import kv.vension.vframe.core.mvp.IPresenter
import kv.vension.vframe.core.mvp.IView

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/25 17:59.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/25 17:59
 * @desc:
 * ===================================================================
 */
interface SelectionTypeContract {

    interface View: IView {
        fun showVertical(verticalBean: VerticalBean)
    }

    interface Presenter : IPresenter<View> {
        fun getVertical(tag: Int)
    }
}