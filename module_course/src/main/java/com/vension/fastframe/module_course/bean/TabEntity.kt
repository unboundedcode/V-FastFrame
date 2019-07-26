package com.vension.fastframe.module_course.bean

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/25 11:58
 * 更 新：2019/7/25 11:58
 * 描 述：底部custom标签类
 * ========================================================
 */

class TabEntity(var title: String, var seledctedIcon: Int, var unSelectedIcon: Int) : CustomTabEntity {

    override fun getTabTitle(): String {
        return title
    }

    override fun getTabSelectedIcon(): Int {
        return seledctedIcon
    }

    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }
}
