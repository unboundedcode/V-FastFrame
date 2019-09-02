package com.vension.fastframe.view

import kv.vension.fastframe.core.IApplication
import kv.vension.fastframe.ext.Logi
import lib.vension.fastframe.common.CommonApplication

/**
 * ========================================================================
 * @author: Created by Vension on 2019/9/2 12:20.
 * @email:  vensionHu@qq.com
 * @Github: https://github.com/Vension
 * __      ________ _   _  _____ _____ ____  _   _
 * \ \    / /  ____| \ | |/ ____|_   _/ __ \| \ | |
 *  \ \  / /| |__  |  \| | (___   | || |  | |  \| |
 *   \ \/ / |  __| | . ` |\___ \  | || |  | | . ` |
 *    \  /  | |____| |\  |____) |_| || |__| | |\  |
 *     \/   |______|_| \_|_____/|_____\____/|_| \_|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * @desc: happy code ->
 * ========================================================================
 */
class ViewApplication : CommonApplication(),IApplication{

    override fun initConfig() {
        Logi("initComponents-->ViewApplication")
    }


    override fun onCreate() {
        super.onCreate()
        initConfig()
    }

}