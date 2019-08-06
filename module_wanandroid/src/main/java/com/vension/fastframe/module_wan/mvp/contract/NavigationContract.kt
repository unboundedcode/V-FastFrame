package com.vension.fastframe.module_wan.mvp.contract

import com.vension.fastframe.module_wan.bean.NavigationBean
import kv.vension.fastframe.core.mvp.IPresenter
import kv.vension.fastframe.core.mvp.IView

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/22 16:44.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
interface NavigationContract {

    interface View : IView {
        fun setNavigationData(list: List<NavigationBean>)
    }

    interface Presenter : IPresenter<View> {
        fun requestNavigationList()
    }

}