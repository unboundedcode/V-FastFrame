package com.vension.fastframe.app.ui.tab1

import kv.vension.fastframe.core.mvp.IPresenter
import kv.vension.fastframe.core.mvp.IViewRefresh
import lib.vension.fastframe.common.test.TestBean

/**
 * ===================================================================
 * @author: Created by Vension on 2019/8/6 15:15.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/8/6 15:15
 * @desc:
 * ===================================================================
 */
interface TabContract_1 {

    interface View : IViewRefresh<TestBean>{
        fun setHomeDatas(datas : MutableList<TestBean>)
    }

    interface Presenter : IPresenter<View> {
        fun getHomeDatas()
    }

}