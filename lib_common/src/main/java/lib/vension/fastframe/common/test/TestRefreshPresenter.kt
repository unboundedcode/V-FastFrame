package lib.vension.fastframe.common.test

import android.os.Handler
import kv.vension.fastframe.core.mvp.AbsPresenter

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/29 10:29.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
class TestRefreshPresenter : AbsPresenter<TestRefreshContract.View>(), TestRefreshContract.Presenter {

    override fun getTestDatas() {
        mView?.showLoading()
        Handler().postDelayed({
            mView?.setTestData(getListData())
        }, 2000)
    }

    private fun getListData(): MutableList<String> {
        var list :MutableList<String> = ArrayList()
        for(i in 0..20){
            list.add("I am test data --> $i")
        }
        return list
    }


}