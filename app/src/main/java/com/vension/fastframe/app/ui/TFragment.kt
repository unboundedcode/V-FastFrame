package com.vension.fastframe.app.ui

import android.os.Bundle
import android.view.View
import com.vension.fastframe.app.R
import com.vension.fastframe.app.bean.TBean
import com.vension.fastframe.app.mvp.contract.TContract
import com.vension.fastframe.app.mvp.presenter.TPresenter
import kotlinx.android.synthetic.main.fragment_observable.*
import kv.vension.fastframe.core.mvp.AbsCompatMVPFragment

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/16 11:43.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/16 11:43
 * @desc:   character determines attitude, attitude determines destiny
 * ===================================================================
 */
class TFragment : AbsCompatMVPFragment<TContract.View, TContract.Presenter>(),
    TContract.View {

    override fun createPresenter(): TContract.Presenter {
        return TPresenter()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_observable
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {

    }

    override fun lazyLoadData() {
        mPresenter.getObjectData()
    }

    override fun showObjectData(objectBean: TBean) {
        textView.text =
            "toString()-----" + objectBean.toString() + "------appId---${objectBean.appId}" + "appkey" + objectBean.appkey
    }
}