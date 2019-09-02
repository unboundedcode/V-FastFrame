package kv.vension.fastframe.image.glide

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.core.util.Preconditions
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.orhanobut.logger.Logger
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.GrayscaleTransformation
import kv.vension.fastframe.R
import kv.vension.fastframe.cache.FileCache
import kv.vension.fastframe.image.ILoaderStrategy
import kv.vension.fastframe.image.ImageLoaderCallback
import kv.vension.fastframe.image.ImageLoaderConfig
import kv.vension.fastframe.image.glide.transformation.CircleBorderTransformation
import kv.vension.fastframe.utils.FileUtil
import java.io.File
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ========================================================================
 * 作 者：Vension
 * 主 页：Github: https://github.com/Vension
 * 日 期：2019/8/28 15:01
 * ______       _____ _______ /\/|_____ _____            __  __ ______
 * |  ____/\    / ____|__   __|/\/  ____|  __ \     /\   |  \/  |  ____|
 * | |__ /  \  | (___    | |     | |__  | |__) |   /  \  | \  / | |__
 * |  __/ /\ \  \___ \   | |     |  __| |  _  /   / /\ \ | |\/| |  __|
 * | | / ____ \ ____) |  | |     | |    | | \ \  / ____ \| |  | | |____
 * |_|/_/    \_\_____/   |_|     |_|    |_|  \_\/_/    \_\_|  |_|______|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * 描述：Glide的策略实现类
 * 复杂的场景可自行实现 [ILoaderStrategy] 和 [ImageLoaderConfig] 替换现有策略
 * ========================================================================
 */

@Singleton
class GlideLoaderStrategy : ILoaderStrategy<GlideConfigImpl>, GlideAppOptions {

    @Inject
    constructor()

    private val cacheThreadPool by lazy {
        Executors.newCachedThreadPool()
    }

    override fun loadImage(context: Context, url: Any, imageView: ImageView) {
        doLoad(context,GlideConfigImpl.builder().url(url).imageView(imageView).build())
    }
    override fun loadImage(context: Context, config: GlideConfigImpl) {
        doLoad(context,config)
    }

    private fun doLoad(context: Context, configImpl: GlideConfigImpl) {
        Preconditions.checkNotNull(context, "Context is required")
        Preconditions.checkNotNull(configImpl, "GlideConfigImpl is required")
        Preconditions.checkNotNull(configImpl!!.imageView, "ImageView is required")

        val requests: GlideRequests = GlideProxy.with(context)

        val requestOptions = RequestOptions()
        requestOptions.transform()
        val glideRequest = requests.load(configImpl.url)

        configImpl?.let {config->

            //缓存策略
            when (config.cacheStrategy) {
                GlideConfigImpl.CacheStrategy.ALL -> glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL)
                GlideConfigImpl.CacheStrategy.NONE -> glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE)
                GlideConfigImpl.CacheStrategy.RESOURCE -> glideRequest.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                GlideConfigImpl.CacheStrategy.DATA -> glideRequest.diskCacheStrategy(DiskCacheStrategy.DATA)
                GlideConfigImpl.CacheStrategy.AUTOMATIC -> glideRequest.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            }

            if (config.isCircle) {
                glideRequest.circleCrop()
            }
            if(config.borderWidth > 0 && config.borderColor > 0){
                glideRequest.transform(CircleBorderTransformation(config.borderWidth, config.borderColor))
            }
            if (config.roundRadius > 0) {
                glideRequest.transform(RoundedCorners(config.roundRadius))
            }
            if (config.blurValue > 0) {
                glideRequest.transform(BlurTransformation(config.blurValue))
            }
            if (config.isCrossFade) {
                glideRequest.transition(DrawableTransitionOptions.withCrossFade())
            }
            if (config.isGray) {
                glideRequest.transform(GrayscaleTransformation())
            }
            if (config.isCenterCrop) {
                glideRequest.centerCrop()
            }
            if (config.thumbnailSize > 0f) {
                glideRequest.thumbnail(config.thumbnailSize)
            }
            //设置占位符
            glideRequest.placeholder(if(config.placeholder > 0 ) config.placeholder else R.color.placeholder_color)
            //设置错误的图片
            glideRequest.error(if(config.errorPic > 0 ) config.errorPic else R.color.placeholder_color)
            //设置请求 url 为空图片
            glideRequest.fallback(if(config.fallback > 0 ) config.fallback else R.color.placeholder_color)
        }
        glideRequest.into(configImpl.imageView!!)
    }

    @SuppressLint("CheckResult")
    override fun getBitmap(context: Context, url: String, imageListener: ImageLoaderCallback<Bitmap>) {
        GlideProxy.with(context).asBitmap().load(url).listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                imageListener?.let {
                    it.onFailed(e?.message.toString())
                }
                return false
            }

            override fun onResourceReady(resource: Bitmap?, model: Any?, target: com.bumptech.glide.request.target.Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                imageListener?.let {
                    it.onSuccess(resource!!)
                }
                return false
            }

        }).submit(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL,com.bumptech.glide.request.target.Target.SIZE_ORIGINAL)
    }



    override fun downLoadImage(context: Context, url: String, saveFile: File, imageListener: ImageLoaderCallback<File>) {
        cacheThreadPool.execute {
            try {
                val sourceFile = GlideProxy.with(context).asFile().load(url).submit().get()
                if (FileUtil.copyFile(sourceFile, saveFile)) {
                    imageListener.onSuccess(saveFile)//回调在后台线程
                }else{
                    imageListener.onFailed("down fail")//回调在后台线程
                }
            } catch (exception:Exception ) {
                exception.printStackTrace()
            }
        }
    }

    override fun clearMemoryCache(context: Context) {
        //Glide要求清除内存缓存需在主线程执行
        if (Looper.myLooper() == Looper.getMainLooper()) {
            GlideProxy.get(context).clearMemory()
        } else {
            Handler(Looper.getMainLooper()).post(Runnable {
                run {
                    GlideProxy.get(context).clearMemory()
                }
            })
        }
    }

    override fun clearDiskCache(context: Context) {
        //Glide要求清除内存缓存需在后台程执行
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Thread(Runnable {
                kotlin.run {
                    GlideProxy.get(context).clearDiskCache()
                }
            }).start()
        } else {
            GlideProxy.get(context).clearDiskCache()
        }
    }

    override fun getCacheSize(context: Context): String {
        return FileUtil.formatFileSize(FileUtil.getFileSize(File(FileCache.getCacheImagePath())))
    }


    override fun applyGlideOptions(context: Context, builder: GlideBuilder) {
        Logger.i("applyGlideOptions")
    }


    /**
     * glide默认缓存路径
     */
    private fun getCacheDir() = InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR


}
