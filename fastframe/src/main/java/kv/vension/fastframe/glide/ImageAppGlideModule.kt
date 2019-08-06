package kv.vension.fastframe.glide

import android.content.ComponentCallbacks2
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.MemoryCategory
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruArrayPool
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskCache
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.engine.executor.GlideExecutor
import com.bumptech.glide.module.AppGlideModule
import kv.vension.fastframe.cache.FileCache


/**
 * ========================================================
 * @author: Created by Vension on 2018/12/4 15:04.
 * @email:  250685***4@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
@GlideModule
class ImageAppGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        // 未捕获异常策略
        val mUncaughtThrowableStrategy = UncaughtThrowableStrategy()
        // 分配内存缓存机制
        val mMemorySizeCalculator = MemorySizeCalculator.Builder(context)
            //				.setLowMemoryMaxSizeMultiplier()
            // 缓存2个屏幕的图片
            .setMemoryCacheScreens(2f)
            //				.setBitmapPoolScreens()
            //				.setMaxSizeMultiplier()
            //				.setArrayPoolSize()
            .build()
        builder.setMemorySizeCalculator(mMemorySizeCalculator)
            //				.setLogLevel(Log.DEBUG)
            .setArrayPool(LruArrayPool(mMemorySizeCalculator.arrayPoolSizeInBytes))
            .setBitmapPool(LruBitmapPool(mMemorySizeCalculator.bitmapPoolSize.toLong()))
            .setMemoryCache(LruResourceCache(mMemorySizeCalculator.memoryCacheSize.toLong()))
            .setDiskCacheExecutor(GlideExecutor.newDiskCacheExecutor(mUncaughtThrowableStrategy))
            .setSourceExecutor(GlideExecutor.newSourceExecutor(mUncaughtThrowableStrategy))
            .setDiskCache(DiskLruCacheFactory(FileCache.getCacheImagePath(),DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE.toLong()))
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        //Glide 默认使用 HttpURLConnection 做网络请求,可在这切换成 Okhttp 请求
        // 高清
        glide.setMemoryCategory(MemoryCategory.HIGH)
        // 当内存比较低时, 释放一些不必要的资源
        glide.trimMemory(ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW)
        // 指定的尺寸下请求图片
        // glide.register(CustomImageSizeModel.class, InputStream.class, new CustomImageSizeModelFactory());
        //		registry.append(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }

    override fun isManifestParsingEnabled(): Boolean {
        // 禁止解析Manifest文件，提升初始化速度，避免一些潜在错误
        return false
    }

    private inner class UncaughtThrowableStrategy : GlideExecutor.UncaughtThrowableStrategy {

        override fun handle(throwable: Throwable) {

        }
    }

}
