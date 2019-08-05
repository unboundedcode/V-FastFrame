package com.vension.fastframe.module_news.mvp.presenter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vension.fastframe.module_news.NewsConstant
import com.vension.fastframe.module_news.bean.VideoNewsModel
import com.vension.fastframe.module_news.mvp.contract.VideoContract
import com.vension.fastframe.module_news.utils.FileUtil
import kv.vension.vframe.VFrame
import kv.vension.vframe.core.mvp.AbsPresenter
import java.lang.reflect.Type

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/30 15:00.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/30 15:00
 * @desc:
 * ===================================================================
 */
class VideoPresenter : AbsPresenter<VideoContract.View>(),
    VideoContract.Presenter {

    override fun getVideoData(flag: Int, videoCurrentType: Int) {
        val multiIndexData = getMultiIndexJsonData(
            if (flag == 1) NewsConstant.getVideoRefreshTypeSource(videoCurrentType) else NewsConstant.getVideoLoadMoreTypeSource(videoCurrentType),
            object : TypeToken<VideoNewsModel>() {}.type)

        mView?.setVideoData(multiIndexData)
    }


    override fun getVideoMoreWares(videoCurrentType: Int) {
        val multiIndexData = getMultiIndexJsonData(
            NewsConstant.getVideoLoadMoreTypeSource(videoCurrentType), object : TypeToken<VideoNewsModel>() {}.type) as VideoNewsModel
        mView?.setVideoMoreWares(multiIndexData)
    }

    /**
     * 解析json
     */
    private fun getMultiIndexJsonData(fileName: String, type: Type): VideoNewsModel? {
        val json = FileUtil.getJson(VFrame.getContext(), fileName)
        val gson = Gson()
        //        Type type = new TypeToken<classa>() {
        //        }.getType();
        return gson.fromJson<VideoNewsModel>(json, type)
    }

}