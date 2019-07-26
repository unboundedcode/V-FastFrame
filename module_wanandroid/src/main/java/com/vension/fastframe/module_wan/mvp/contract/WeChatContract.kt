package com.vension.fastframe.module_wan.mvp.contract

import com.vension.fastframe.module_wan.bean.WXChapterBean
import kv.vension.vframe.core.mvp.IPresenter
import kv.vension.vframe.core.mvp.IView

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/23 10:36.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
interface WeChatContract {

    interface View : IView {
        fun showWXChapters(chapters: MutableList<WXChapterBean>)
    }

    interface Presenter : IPresenter<View> {
        fun getWXChapters()
    }

}