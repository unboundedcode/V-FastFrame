/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vension.fastframe.app.ui.image

import android.widget.ImageView
import kv.vension.fastframe.image.ImageLoaderConfig

/**
 * ========================================================================
 * 作 者：Vension
 * 主 页：Github: https://github.com/Vension
 * 日 期：2019/8/28 14:56
 * ______       _____ _______ /\/|_____ _____            __  __ ______
 * |  ____/\    / ____|__   __|/\/  ____|  __ \     /\   |  \/  |  ____|
 * | |__ /  \  | (___    | |     | |__  | |__) |   /  \  | \  / | |__
 * |  __/ /\ \  \___ \   | |     |  __| |  _  /   / /\ \ | |\/| |  __|
 * | | / ____ \ ____) |  | |     | |    | | \ \  / ____ \| |  | | |____
 * |_|/_/    \_\_____/   |_|     |_|    |_|  \_\/_/    \_\_|  |_|______|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * 描述：这里存放图片请求的配置信息,可以一直扩展字段,如果外部调用时想让图片加载框架
 * 做一些操作,比如清除缓存或者切换缓存策略,则可以定义一个 int 类型的变量,内部根据 switch(int) 做不同的操作
 * 其他操作同理
 * ========================================================================
 */
class UniversalConfigImpl private constructor(builder: Builder) : ImageLoaderConfig() {

    val fallback: Int //请求 url 为空,则使用此图片作为占位符
    val isCircle: Boolean//是否将图片剪切为圆形
    val borderColor: Int//边框颜色，仅在圆形模式下有效
    val borderWidth: Float//边框粗细，单位dp，仅在圆形模式下有效
    val roundRadius: Int//加载为圆角图片的圆角值
    val isCacheInMemory: Boolean//是否需要缓存在内存中
    val isCacheOnDisk: Boolean//是否需要缓存在内存中

    init {
        this.url = builder.url
        this.imageView = builder.imageView
        this.placeholder = builder.placeholder
        this.errorPic = builder.errorPic
        this.fallback = builder.fallback
        this.isCircle = builder.isCircle
        this.borderColor = builder.borderColor
        this.borderWidth = builder.borderWidth
        this.roundRadius = builder.roundRadius
        this.isCacheInMemory = builder.isCacheInMemory
        this.isCacheOnDisk = builder.isCacheInMemory
    }

    class Builder {
        var url: Any? = null
        var imageView: ImageView? = null
        var placeholder: Int = 0
        var errorPic: Int = 0
        var fallback: Int = 0 //请求 url 为空,则使用此图片作为占位符
        var isCircle: Boolean = false//是否将图片剪切为圆形
        var borderColor: Int = 0//边框颜色，仅在圆形模式下有效
        var borderWidth: Float = 0f//边框粗细，单位dp，仅在圆形模式下有效
        var roundRadius: Int = 0//图片每个圆角的大小
        var isCacheInMemory: Boolean = false//
        var isCacheOnDisk: Boolean = false//

        fun url(url: Any): Builder {
            this.url = url
            return this
        }
        fun imageView(imageView: ImageView): Builder {
            this.imageView = imageView
            return this
        }
        fun placeholder(placeholder: Int): Builder {
            this.placeholder = placeholder
            return this
        }
        fun errorPic(errorPic: Int): Builder {
            this.errorPic = errorPic
            return this
        }
        fun fallback(fallback: Int): Builder {
            this.fallback = fallback
            return this
        }
        fun isCircle(isCircle: Boolean): Builder {
            this.isCircle = isCircle
            return this
        }
        fun borderColor(borderColor: Int): Builder {
            this.borderColor = borderColor
            return this
        }
        fun borderWidth(borderWidth: Float): Builder {
            this.borderWidth = borderWidth
            return this
        }
        fun roundRadius(roundRadius: Int): Builder {
            this.roundRadius = roundRadius
            return this
        }
        fun isCacheInMemory(isCacheInMemory: Boolean): Builder {
            this.isCacheInMemory = isCacheInMemory
            return this
        }
        fun isCacheOnDisk(isCacheOnDisk: Boolean): Builder {
            this.isCacheOnDisk = isCacheOnDisk
            return this
        }
        fun build(): UniversalConfigImpl {
            return UniversalConfigImpl(this)
        }

    }


    companion object {

        fun builder(): Builder {
            return Builder()
        }
    }

}
