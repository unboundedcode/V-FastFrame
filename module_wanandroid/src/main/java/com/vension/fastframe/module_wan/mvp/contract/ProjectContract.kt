package com.vension.fastframe.module_wan.mvp.contract

import com.vension.fastframe.module_wan.bean.ProjectTreeBean
import kv.vension.fastframe.core.mvp.IPresenter
import kv.vension.fastframe.core.mvp.IView

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/22 15:11.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
interface ProjectContract {

    interface View : IView {
        fun setProjectTree(list: List<ProjectTreeBean>)
    }

    interface Presenter : IPresenter<View> {
        fun requestProjectTree()
    }

}