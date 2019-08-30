package kv.vension.fastframe.image

import android.widget.ImageView

/**
 * ========================================================================
 * 作 者：Vension
 * 主 页：Github: https://github.com/Vension
 * 日 期：2019/8/23 17:41
 * ______       _____ _______ /\/|_____ _____            __  __ ______
 * |  ____/\    / ____|__   __|/\/  ____|  __ \     /\   |  \/  |  ____|
 * | |__ /  \  | (___    | |     | |__  | |__) |   /  \  | \  / | |__
 * |  __/ /\ \  \___ \   | |     |  __| |  _  /   / /\ \ | |\/| |  __|
 * | | / ____ \ ____) |  | |     | |    | | \ \  / ____ \| |  | | |____
 * |_|/_/    \_\_____/   |_|     |_|    |_|  \_\/_/    \_\_|  |_|______|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * 描述：加载图片框架的一些参数的配置；
 * 每个 [ILoaderStrategy] 应该对应一个 [ImageLoaderConfig] 实现类
 * 用到了Builder模式，一步一步的创建一个复杂对象的创建者模式，
 * 它允许用户在不知道内部构建细节的情况下，可以更精细的控制对象的构建流程
 * ========================================================================
 */
open class ImageLoaderConfig {

     var url: Any? = null
          protected set
     var imageView: ImageView? = null
          protected set
     var placeholder: Int = 0
          protected set//占位符
     var errorPic: Int = 0
          protected set//错误占位符

}