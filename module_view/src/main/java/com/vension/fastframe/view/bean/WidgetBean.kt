package com.vension.fastframe.view.bean

import androidx.annotation.DrawableRes

/**
 * ========================================================================
 * @author: Created by Vension on 2019/9/2 15:10.
 * @email : vensionHu@qq.com
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
data class WidgetBean(@DrawableRes var imgBg:Int,var name:String,var desc:String) {
}

data class HomePlanetBean(
    val planetName: String,//行星名字
    @DrawableRes val planetNormalImage: Int,//行星未激活图片
    @DrawableRes val planetActivateImage: Int,// 行星已激活图片
    val needStars: Int, // 需要多少星星才能激活
    val isActivated: Boolean, // 是否已激活
    val canActivate: Boolean // 是否可以激活
)