package com.vension.fastframe.app.ui.tab1

import android.os.Handler
import kv.vension.fastframe.core.mvp.AbsPresenter
import lib.vension.fastframe.common.test.TestBean

/**
 * ===================================================================
 * @author: Created by Vension on 2019/8/6 15:17.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/8/6 15:17
 * @desc:
 * ===================================================================
 */
class TabPresenter_1 : AbsPresenter<TabContract_1.View>(),TabContract_1.Presenter{

    override fun getHomeDatas() {
        mView?.showLoading()
        Handler().postDelayed({
            var list :MutableList<TestBean> = ArrayList()
            list.add(TestBean(0,"I am test banners"))
            list.add(TestBean(1,"I am test viewFlipper"))
            list.add(TestBean(2,"I am test gridMenu"))
            list.add(TestBean(3,"I am test threeMenu"))
            list.add(TestBean(5,"I am test listItem"))
            list.add(TestBean(5,"I am test listItem"))
            list.add(TestBean(5,"I am test listItem"))
            list.add(TestBean(5,"I am test listItem"))
            list.add(TestBean(5,"I am test listItem"))
            mView?.setHomeDatas(list)
        }, 2000)
    }
}