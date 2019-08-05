package com.vension.fastframe.module_news.bean

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/29 15:38.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/29 15:38
 * @desc:
 * ===================================================================
 */
class TabEntity(var title: String,var selectedIcon: Int = 0,var unSelectedIcon: Int = 0) : CustomTabEntity {


    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabTitle(): String {
        return title
    }
}