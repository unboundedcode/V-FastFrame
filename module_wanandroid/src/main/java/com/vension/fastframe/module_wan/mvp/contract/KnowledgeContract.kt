package com.vension.fastframe.module_wan.mvp.contract

import com.vension.fastframe.module_wan.bean.ArticleResponseBody

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/21 11:01.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
interface KnowledgeContract {

    interface View<data> : CommonContract.View<data> {
        fun setKnowledgeList(page: Int,articles : ArticleResponseBody)
    }

    interface Presenter<data> : CommonContract.Presenter<data, View<data>> {
        fun requestKnowledgeList(page: Int, cid: Int)
    }

}