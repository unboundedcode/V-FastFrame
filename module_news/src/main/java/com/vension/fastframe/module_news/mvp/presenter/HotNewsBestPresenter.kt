package com.vension.fastframe.module_news.mvp.presenter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vension.fastframe.module_news.NewsConstant
import com.vension.fastframe.module_news.bean.HotNewsBestModel
import com.vension.fastframe.module_news.mvp.contract.HotNewsBestContract
import com.vension.fastframe.module_news.utils.FileUtil
import kv.vension.vframe.VFrame
import kv.vension.vframe.core.mvp.AbsPresenter
import java.io.IOException
import java.io.InputStream

/**
 * ===================================================================
 * @author: Created by Vension on 2019/8/5 10:25.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/8/5 10:25
 * @desc:
 * ===================================================================
 */
class HotNewsBestPresenter : AbsPresenter<HotNewsBestContract.View>(),HotNewsBestContract.Presenter {
    override fun getHotNewsBestData() {
        val multiIndexData = getMultiIndexJsonData(NewsConstant.ASSET_HOT_NEWS_BEST) as HotNewsBestModel?
        mView?.setHotNewsBestData(multiIndexData)
    }


    private fun getMultiIndexJsonData(fileName: String): HotNewsBestModel? {
        val json = FileUtil.getJson(VFrame.getContext(), fileName)
        val gson = Gson()
        val type = object : TypeToken<HotNewsBestModel>() {}.type
        return gson.fromJson(json, type)
    }


    private fun getMultiIndexData(clazz : Class<HotNewsBestModel>,fillName: String) : HotNewsBestModel?{
        var `is`: InputStream? = null
        var s: HotNewsBestModel? = null
        try {
            `is` = VFrame.getAssets().open(fillName)
            val text = FileUtil.readTextFromFile(`is`)
            val gson = Gson()
            s = gson.fromJson(text, clazz)
        }catch (e : IOException) {
            e.printStackTrace()
        } catch (e : Exception) {
            e.printStackTrace()
        }
        return s
    }


}