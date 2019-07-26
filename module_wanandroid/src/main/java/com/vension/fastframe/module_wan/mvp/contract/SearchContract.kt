package com.vension.fastframe.module_wan.mvp.contract

import com.vension.fastframe.module_wan.bean.HotSearchBean
import com.vension.fastframe.module_wan.bean.SearchHistoryBean
import kv.vension.vframe.core.mvp.IPresenter
import kv.vension.vframe.core.mvp.IView

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/23 14:34.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
interface SearchContract {

    interface View : IView {

        fun showHistoryData(historyBeans: MutableList<SearchHistoryBean>)

        fun showHotSearchData(hotSearchDatas: MutableList<HotSearchBean>)

    }

    interface Presenter : IPresenter<View> {

        fun queryHistory()

        fun saveSearchKey(key: String)

        fun deleteById(id: Long)

        fun clearAllHistory()

        fun getHotSearchData()

    }


}