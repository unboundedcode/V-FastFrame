package com.vension.fastframe.app.ui

import android.os.Bundle
import android.view.View
import com.vension.fastframe.app.R
import com.vension.fastframe.app.bean.ObjectBean
import com.vension.fastframe.app.mvp.contract.FlowableContract
import com.vension.fastframe.app.mvp.presenter.FlowablePresenter
import kotlinx.android.synthetic.main.fragment_observable.*
import kv.vension.vframe.core.mvp.AbsCompatMVPFragment

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/16 11:43.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/16 11:43
 * @desc:   character determines attitude, attitude determines destiny
 * ===================================================================
 */
class FlowableFragment : AbsCompatMVPFragment<FlowableContract.View, FlowableContract.Presenter>(),
    FlowableContract.View {
    override fun createPresenter(): FlowableContract.Presenter {
        return FlowablePresenter()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_observable
    }


    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {

    }

    override fun lazyLoadData() {
        mPresenter.getObjectData()
    }

    override fun showObjectData(objectBean: ObjectBean) {
        textView.text =
            "toString()-----" + objectBean.toString() + "------code---${objectBean.code}" + "msg" + objectBean.msg
    }
}