package com.vension.fastframe.module_course.mvp.contract

import com.vension.fastframe.module_course.bean.MineBean
import kv.vension.fastframe.core.mvp.IPresenter
import kv.vension.fastframe.core.mvp.IView

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/25 11:26.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/25 11:26
 * @desc:   character determines attitude, attitude determines destiny
 * ===================================================================
 */
interface MineContract {

    interface View : IView {
        fun showMine(mineBean: MineBean)
    }

    interface Presenter : IPresenter<View> {
        fun getMine()
    }

}