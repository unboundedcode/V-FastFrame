package lib.vension.fastframe.common.test

import kv.vension.vframe.core.mvp.IPresenter
import kv.vension.vframe.core.mvp.IViewRefresh

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/29 10:23.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
interface TestRefreshContract {

    interface View : IViewRefresh<String> {
        fun setTestData(testDatas : MutableList<String>)
    }

    interface Presenter : IPresenter<View> {
       fun getTestDatas()
    }

}