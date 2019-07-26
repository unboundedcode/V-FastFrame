package com.vension.fastframe.app.mvp.contract

import kv.vension.vframe.core.mvp.IPresenter
import kv.vension.vframe.core.mvp.IView
import com.vension.fastframe.app.bean.TBean


/**
 *
 */
interface TContract {

    interface View : IView {
        /**
         * 显示创建应用接口
         */
        fun showObjectData(objectBean: TBean)

    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取创建应用接口
         */
        fun getObjectData()
    }
}