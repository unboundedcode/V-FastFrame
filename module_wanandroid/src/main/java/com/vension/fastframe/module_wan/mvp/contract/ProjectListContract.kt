package com.vension.fastframe.module_wan.mvp.contract

import com.vension.fastframe.module_wan.bean.ArticleResponseBody

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/22 15:39.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
interface ProjectListContract {

    interface View<data> : CommonContract.View<data> {
        fun setProjectList(page: Int,articles: ArticleResponseBody)
    }

    interface Presenter<data> : CommonContract.Presenter<data, View<data>> {
        fun requestProjectList(page: Int, cid: Int)
    }

}