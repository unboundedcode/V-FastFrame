package com.vension.fastframe.module_news

import kv.vension.fastframe.core.IApplication
import kv.vension.fastframe.ext.Logi
import lib.vension.fastframe.common.CommonApplication


/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/17 20:08.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/17 20:08
 * @desc:   character determines attitude, attitude determines destiny
 * ===================================================================
 */
class NewsApplication : CommonApplication(), IApplication {

    override fun initConfig() {
        Logi("initComponents-->NewsApplication")
    }


    override fun onCreate() {
        super.onCreate()
        initConfig()
    }

}