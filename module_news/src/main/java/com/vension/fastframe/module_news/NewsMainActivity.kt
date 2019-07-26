package com.vension.fastframe.module_news

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import kv.vension.vframe.core.AbsCompatActivity
import lib.vension.fastframe.common.RouterConfig

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/17 20:09.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/17 20:09
 * @desc:   character determines attitude, attitude determines destiny
 * ===================================================================
 */

@Route(path = RouterConfig.PATH_MODULE_NEWS_MAINACTIVITY)
class NewsMainActivity : AbsCompatActivity() {


    override fun attachLayoutRes(): Int {
        return R.layout.activity_main_news
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
    }
}