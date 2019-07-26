package com.vension.fastframe.module_wan.mvp.contract

import com.vension.fastframe.module_wan.bean.ArticleResponseBody
import com.vension.fastframe.module_wan.bean.Banner

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/23 14:19.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/23 14:19
 * @desc:   character determines attitude, attitude determines destiny
 * ===================================================================
 */
interface HomeContract {

    interface View<data> : CommonContract.View<data> {

        fun setBanners(banners:List<Banner>)

        fun setArticles(articles: ArticleResponseBody)
    }

    interface Presenter<data> : CommonContract.Presenter<data, View<data>> {

        fun requestHomeData()

        fun requestBanner()

        fun requestArticles(num: Int)

    }

}