package com.vension.fastframe.module_news.mvp.contract

import com.vension.fastframe.module_news.bean.NewsDetailModel
import kv.vension.fastframe.core.mvp.IPresenter
import kv.vension.fastframe.core.mvp.IView

/**
 * ===================================================================
 * @author: Created by Vension on 2019/8/2 17:03.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/8/2 17:03
 * @desc:
 * ===================================================================
 */
interface NewsDetailContract {

    interface View : IView {
        fun detailItemData(data: NewsDetailModel)
    }

    interface Presenter: IPresenter<View> {
        fun deatailItemRequest(articleUrl: String)
    }

}