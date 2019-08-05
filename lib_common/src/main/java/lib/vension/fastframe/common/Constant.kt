package lib.vension.fastframe.common

import java.util.*

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/29 14:20.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/29 14:20
 * @desc:
 * ===================================================================
 */
object Constant{


    /**
     * 随机获取图
     */
    fun getSplashImg(): Int {
        val list = ArrayList<Int>()
        list.add(R.drawable.img_splash)
        list.add(R.drawable.img_advertisment)
        list.add(R.drawable.img_welcome_1)
        list.add(R.drawable.img_welcome_2)
        list.add(R.drawable.img_welcome_3)
        list.add(R.drawable.img_welcome_4)
        list.add(R.drawable.gif_01)
        list.add(R.drawable.gif_02)
        list.add(R.drawable.gif_03)
        list.add(R.drawable.gif_04)
        list.add(R.drawable.gif_05)
        list.add(R.drawable.gif_06)
        list.add(R.drawable.gif_07)
        list.add(R.drawable.gif_08)
        return list[Random().nextInt(list.size)]
    }

}