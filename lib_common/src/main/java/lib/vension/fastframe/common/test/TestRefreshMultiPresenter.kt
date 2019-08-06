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
class TestRefreshMultiPresenter : AbsPresenter<TestRefreshMultiContract.View>(), TestRefreshMultiContract.Presenter {


    override fun getTestMultiDatas() {
        var list :MutableList<TestBean> = ArrayList()
        for(i in 0..20){
            list.add(TestBean(i%3,"I am test data"))
        }
        mView?.showLoading()
        Handler().postDelayed({
            mView?.setTestMultiData(list)
        }, 2000)
    }

}