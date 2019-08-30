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
package kv.vension.fastframe.image.glide

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
class GlideConfigImpl private constructor(builder: Builder) : ImageLoaderConfig() {

    val cacheStrategy: CacheStrategy//0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
    val fallback: Int //请求 url 为空,则使用此图片作为占位符
    val isCircle: Boolean//是否将图片剪切为圆形
    val borderColor: Int//边框颜色，仅在圆形模式下有效
    val borderWidth: Int//边框粗细，单位dp，仅在圆形模式下有效
    val roundRadius: Int//加载为圆角图片的圆角值
    val blurValue: Int//高斯模糊值, 值越大模糊效果越大
    val isCrossFade: Boolean//是否使用淡入淡出过渡动画
    val isCenterCrop: Boolean//是否将图片剪切为 CenterCrop
    val isGray: Boolean//是否加载为灰白图片
    val thumbnailSize:Float//缩略图质量比

    init {
        this.url = builder.url
        this.imageView = builder.imageView
        this.placeholder = builder.placeholder
        this.errorPic = builder.errorPic
        this.cacheStrategy = builder.cacheStrategy
        this.fallback = builder.fallback
        this.isCircle = builder.isCircle
        this.borderColor = builder.borderColor
        this.borderWidth = builder.borderWidth
        this.roundRadius = builder.roundRadius
        this.blurValue = builder.blurValue
        this.isCrossFade = builder.isCrossFade
        this.isCenterCrop = builder.isCenterCrop
        this.isGray = builder.isGray
        this.thumbnailSize = builder.thumbnailSize
    }

    class Builder {
        var url: Any? = null
        var imageView: ImageView? = null
        var placeholder: Int = 0
        var errorPic: Int = 0
        var cacheStrategy: CacheStrategy = CacheStrategy.ALL//0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
        var fallback: Int = 0 //请求 url 为空,则使用此图片作为占位符
        var isCircle: Boolean = false//是否将图片剪切为圆形
        var borderColor: Int = 0//边框颜色，仅在圆形模式下有效
        var borderWidth: Int = 0//边框粗细，单位dp，仅在圆形模式下有效
        var roundRadius: Int = 0//图片每个圆角的大小
        var blurValue: Int = 0//高斯模糊值, 值越大模糊效果越大
        var isCrossFade: Boolean = false//是否使用淡入淡出过渡动画
        var isCenterCrop: Boolean = false//是否将图片剪切为 CenterCrop
        var isGray: Boolean = false//是否加载为灰白图片
        var thumbnailSize: Float = 0f//是否加载为灰白图片

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
        fun cacheStrategy(cacheStrategy: CacheStrategy): Builder {
            this.cacheStrategy = cacheStrategy
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
        fun borderWidth(borderWidth: Int): Builder {
            this.borderWidth = borderWidth
            return this
        }
        fun roundRadius(roundRadius: Int): Builder {
            this.roundRadius = roundRadius
            return this
        }
        fun blurValue(blurValue: Int): Builder { //blurValue 建议设置为 15
            this.blurValue = blurValue
            return this
        }

        fun isCrossFade(isCrossFade: Boolean): Builder {
            this.isCrossFade = isCrossFade
            return this
        }

        fun isCenterCrop(isCenterCrop: Boolean): Builder {
            this.isCenterCrop = isCenterCrop
            return this
        }

        fun isGray(isGray: Boolean): Builder {
            this.isGray = isGray
            return this
        }
        fun thumbnailSize(url: Float): Builder {
            this.thumbnailSize = thumbnailSize
            return this
        }
        fun build(): GlideConfigImpl {
            return GlideConfigImpl(this)
        }

    }


    /**
     * 图片缓存策略
     */
    enum class CacheStrategy {
        ALL,
        NONE,
        RESOURCE,
        DATA,
        AUTOMATIC
    }

    companion object {

        fun builder(): Builder {
            return Builder()
        }
    }

}
