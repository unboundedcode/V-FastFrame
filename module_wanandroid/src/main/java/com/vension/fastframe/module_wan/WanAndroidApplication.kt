package com.vension.fastframe.module_wan

import kv.vension.vframe.core.IApplication
import kv.vension.vframe.ext.Logi
import lib.vension.fastframe.common.CommonApplication
import org.litepal.LitePal

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/17 20:08.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/17 20:08
 * @desc:   character determines attitude, attitude determines destiny
 * ===================================================================
 */
class WanAndroidApplication : CommonApplication(), IApplication {

    override fun initConfig() {
        Logi("initComponents-->WanAndroidApplication")
        LitePal.initialize(this)//初始化 LitePal
    }


    override fun onCreate() {
        super.onCreate()
        initConfig()
    }

}