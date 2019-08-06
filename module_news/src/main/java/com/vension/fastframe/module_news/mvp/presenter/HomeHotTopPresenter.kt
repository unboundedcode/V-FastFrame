package com.vension.fastframe.module_news.mvp.presenter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vension.fastframe.module_news.NewsConstant
import com.vension.fastframe.module_news.bean.HomeTophotIndex
import com.vension.fastframe.module_news.mvp.contract.HomeHotTopContract
import com.vension.fastframe.module_news.utils.FileUtil
import kv.vension.fastframe.VFrame
import kv.vension.fastframe.core.mvp.AbsPresenter

/**
 * ===================================================================
 * @author: Created by Vension on 2019/8/1 11:47.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/8/1 11:47
 * @desc:
 * ===================================================================
 */
class HomeHotTopPresenter : AbsPresenter<HomeHotTopContract.View>(),HomeHotTopContract.Presenter {

    override fun getHomeHotTopData(flag: Int, videoCurrentType: Int) {
        mView?.setHomeHotTopData(
            getMultiIndexJsonData(if (flag == 1) NewsConstant.getHomeRefreshTypeSource(videoCurrentType) else NewsConstant.getHomeRefreshTypeSource(videoCurrentType))
        )
    }

    override fun getHomeHotTopWares(videoCurrentType: Int) {
        mView?.setHomeHotTopWares(getMultiIndexJsonData(NewsConstant.ASSET_HOME_HOT_TOP))
    }

    override fun getMoreHomeHotTopWares(videoCurrentType: Int) {
        mView?.setMoreHomeHotTopWares(
            getMultiIndexJsonData(NewsConstant.getHomeLoadMoreTypeSource(videoCurrentType))
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