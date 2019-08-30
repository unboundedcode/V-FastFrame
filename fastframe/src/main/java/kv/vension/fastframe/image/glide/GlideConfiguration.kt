package kv.vension.fastframe.image.glide

import android.content.ComponentCallbacks2
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.bitmap_recycle.LruArrayPool
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskCache
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.engine.executor.GlideExecutor
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import kv.vension.fastframe.cache.FileCache
import kv.vension.fastframe.image.ImageLoaderHelper
import java.io.InputStream


/**
 * ========================================================================
 * 作 者：Vension
 * 主 页：Github: https://github.com/Vension
 * 日 期：2019/8/26 16:40
 *   ______       _____ _______ /\/|_____ _____            __  __ ______
 *  |  ____/\    / ____|__   __|/\/  ____|  __ \     /\   |  \/  |  ____|
 *  | |__ /  \  | (___    | |     | |__  | |__) |   /  \  | \  / | |__
 *  |  __/ /\ \  \___ \   | |     |  __| |  _  /   / /\ \ | |\/| |  __|
 *  | | / ____ \ ____) |  | |     | |    | | \ \  / ____ \| |  | | |____
 *  |_|/_/    \_\_____/   |_|     |_|    |_|  \_\/_/    \_\_|  |_|______|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * 描述： Glide配置类，在这里配置缓存以及网络组件等
 * 因为该类已经继承了AppGlideModule，所以主项目中不能再有类继承AppGlideModule来做自定义模块，
 * 否则会报com.android.dex.DexException: Multiple dex files define Lcom/bumptech/glide/GeneratedAppGlideModuleImpl异常，
 * 但允许有多个继承LibraryGlideModule自定义registerComponents模块。
 * ========================================================================
 */
@GlideModule(glideName = "GlideProxy")
class GlideConfiguration : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)

        // 未捕获异常策略
        val mUncaughtThrowableStrategy = UncaughtThrowableStrategy()

        // 分配内存缓存机制
        val mMemorySizeCalculator = MemorySizeCalculator.Builder(context).build()
        builder
            .setMemorySizeCalculator(mMemorySizeCalculator)
            .setDiskCache(DiskLruCacheFactory(FileCache.getCacheImagePath(), DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE.toLong()))
            .setMemoryCache(LruResourceCache((1.2 * mMemorySizeCalculator.memoryCacheSize).toLong()))
            .setBitmapPool(LruBitmapPool((1.2 * mMemorySizeCalculator.bitmapPoolSize).toLong()))
            .setArrayPool(LruArrayPool(mMemorySizeCalculator.arrayPoolSizeInBytes))
            .setDiskCacheExecutor(GlideExecutor.newDiskCacheExecutor(mUncaughtThrowableStrategy))
            .setSourceExecutor(GlideExecutor.newSourceExecutor(mUncaughtThrowableStrategy))


        //将配置 Glide 的机会转交给 GlideImageLoaderStrategy,如你觉得框架提供的 GlideImageLoaderStrategy
        //并不能满足自己的需求,想自定义 BaseImageLoaderStrategy,那请你最好实现 GlideAppOptions
        //因为只有成为 GlideAppOptions 的实现类,这里才能调用 applyGlideOptions(),让你具有配置 Glide 的权利
        val loadImgStrategy = ImageLoaderHelper.getInstance().imageLoaderStrategy
//        val loadImgStrategy = ImageLoaderHelper.getsInstance()?.imageLoaderStrategy
        if (loadImgStrategy != null && loadImgStrategy is GlideAppOptions) {
            (loadImgStrategy as GlideAppOptions).applyGlideOptions(context, builder)
        }

    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        //Glide 默认使用 HttpURLConnection 做网络请求,可在这切换成 Okhttp 请求
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory())
        // 当内存比较低时, 释放一些不必要的资源
        glide.trimMemory(ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW)
        // 指定的尺寸下请求图片
        // glide.register(CustomImageSizeModel.class, InputStream.class, new CustomImageSizeModelFactory());
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
