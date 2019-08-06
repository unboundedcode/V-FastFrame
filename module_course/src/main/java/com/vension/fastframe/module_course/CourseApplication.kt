package com.vension.fastframe.module_course

import kv.vension.fastframe.core.IApplication
import kv.vension.fastframe.ext.Logi
import lib.vension.fastframe.common.CommonApplication

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/25 10:10.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/25 10:10
 * @desc:   character determines attitude, attitude determines destiny
 * ===================================================================
 */
class CourseApplication : CommonApplication(), IApplication {

    override fun initConfig() {
        Logi("initComponents-->CourseApplication")
    }

    override fun onCreate() {
        super.onCreate()
        initConfig()
    }
}