package com.vension.fastframe.module_news.mvp.presenter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vension.fastframe.module_news.NewsConstant
import com.vension.fastframe.module_news.bean.HomeTophotIndex
import com.vension.fastframe.module_news.mvp.contract.HomeRecommendContract
import com.vension.fastframe.module_news.utils.FileUtil
import kv.vension.fastframe.VFrame
import kv.vension.fastframe.core.mvp.AbsPresenter

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/29 10:29.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
class HomeRecommendPresenter : AbsPresenter<HomeRecommendContract.View>(), HomeRecommendContract.Presenter {


    override fun getHomeHotTopData(flag: Int, videoCurrentType: Int) {
        mView?.setHomeHotTopData(
            getMultiIndexJsonData(if (flag == 1) NewsConstant.getHomeRefreshTypeSource(videoCurrentType) else NewsConstant.getHomeRefreshTypeSource(videoCurrentType))
        )
    }

    /**
     * 解析json
     */
    private fun getMultiIndexJsonData(fileName: String): HomeTophotIndex {
        val json = FileUtil.getJson(VFrame.getContext(), fileName)
        val gson = Gson()
        val type = object : TypeToken<HomeTophotIndex>() {}.type
        return gson.fromJson(json, type)
    }

}