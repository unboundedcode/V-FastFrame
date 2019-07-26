package com.vension.fastframe.module_wan.mvp.contract

import com.vension.fastframe.module_wan.bean.KnowledgeTreeBody
import kv.vension.vframe.core.mvp.IPresenter
import kv.vension.vframe.core.mvp.IViewRefresh

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/7 15:39.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
interface KnowledgeTreeContract {

    interface View : IViewRefresh<KnowledgeTreeBody> {

    }

    interface Presenter : IPresenter<View> {

        fun requestKnowledgeTree()

    }

}