package com.vension.fastframe.module_course.mvp.contract

import com.vension.fastframe.module_course.bean.SelectionBean
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
interface SelectionContract {

    interface View: IView {
        fun showSelection(selectionBean: SelectionBean)
    }

    interface Presenter : IPresenter<View> {
        fun getSelection()
    }
}