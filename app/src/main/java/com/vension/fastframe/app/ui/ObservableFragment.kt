package com.vension.fastframe.app.ui

import android.os.Bundle
import android.view.View
import com.vension.fastframe.app.R
import com.vension.fastframe.app.bean.ObjectBean
import com.vension.fastframe.app.mvp.contract.ObservableContract
import com.vension.fastframe.app.mvp.presenter.ObservablePresenter
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
class ObservableFragment : AbsCompatMVPFragment<ObservableContract.View, ObservableContract.Presenter>(),
    ObservableContract.View {
    override fun createPresenter(): ObservableContract.Presenter {
        return ObservablePresenter()
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