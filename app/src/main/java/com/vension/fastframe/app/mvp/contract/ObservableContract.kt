package com.vension.fastframe.app.mvp.contract

import com.vension.fastframe.app.bean.ObjectBean
import kv.vension.fastframe.core.mvp.IPresenter
import kv.vension.fastframe.core.mvp.IView


/**
 *
 */
interface ObservableContract {

    interface View : IView {
        /**
         * 显示创建应用接口
         */
        fun showObjectData(objectBean: ObjectBean)

    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取创建应用接口
         */
        fun getObjectData()
    }
}