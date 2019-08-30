//package kv.vension.fastframe.image
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.graphics.Bitmap
//import android.widget.ImageView
//import androidx.core.util.Preconditions
//import kv.vension.fastframe.R
//import java.io.File
//
///**
// * @author Created by：Fanson
// * Created Time: 2018/10/19 14:32
// * Describe：
// */
///**
// * ========================================================================
// * 作 者：Vension
// * 主 页：Github: https://github.com/Vension
// * 日 期：2019/8/26 12:16
// * ______       _____ _______ /\/|_____ _____            __  __ ______
// * |  ____/\    / ____|__   __|/\/  ____|  __ \     /\   |  \/  |  ____|
// * | |__ /  \  | (___    | |     | |__  | |__) |   /  \  | \  / | |__
// * |  __/ /\ \  \___ \   | |     |  __| |  _  /   / /\ \ | |\/| |  __|
// * | | / ____ \ ____) |  | |     | |    | | \ \  / ____ \| |  | | |____
// * |_|/_/    \_\_____/   |_|     |_|    |_|  \_\/_/    \_\_|  |_|______|
// *
// * Take advantage of youth and toss about !
// * ------------------------------------------------------------------------
// * 描述：图片加载框架的代理类，提供对外接口。
// * [ImageLoaderHelper] 使用策略模式和建造者模式,可以动态切换图片请求框架
// *
// * 静态代理模式，开发者只需要关心ILoaderStrategy + ImageLoaderConfig
// * @use
// * 1、初始化
// * ImageLoaderHelper.INSTANCE.setImageLoaderStrategy(GlideLoaderStrategy())
// * 2、普通下载图片
// * ImageLoaderHelper.INSTANCE.loadImage(mContext,url,iv)
// * ========================================================================
// */
//
//class ImageLoaderHelper private constructor() {
//
//    private lateinit var mOptions: ImageLoaderConfig
//
//    /**
//     * 全局单例实现,延迟加载
//     */
//    companion object {
//        val INSTANCE : ImageLoaderHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { ImageLoaderHelper() }
//        private var mLoaderStrategy: ILoaderStrategy<*>? = null
//    }
//
//    /**
//     * 初始化并设置图片加载策略
//     * 在[android.app.Application]中
//     */
//    fun init(loaderStrategy: ILoaderStrategy<*>) {
//        mLoaderStrategy = loaderStrategy
//    }
//
//    /**
//     * 借助于kotlin[apply]方法以及lambda表达式来构造生成[ImageLoaderConfig]
//     * 简化代码，提升可读性
//     */
//    fun with(data: ImageLoaderConfig.() -> Unit): ImageLoaderHelper {
//        mOptions = ImageLoaderConfig().apply(data)
//        return this
//    }
//
//
//    /**
//     * 可在运行时随意切换 [ILoaderStrategy]
//     *
//     * @param strategy
//     */
//    fun setImageLoaderStrategy(strategy: ILoaderStrategy<*>) {
//        Preconditions.checkNotNull(strategy, "strategy == null")
//        mLoaderStrategy = strategy
//    }
//
//    fun getImageLoaderStrategy(): ILoaderStrategy<*>? {
//        return mLoaderStrategy
//    }
//
//
//    fun loadImage(ctx: Context, url: Any, imageView: ImageView) {
//        Preconditions.checkNotNull(mLoaderStrategy, "Please implement ILoaderStrategy")
//        mLoaderStrategy?.loadImage(ctx, url, imageView) ?: error()
//    }
//
//    /**
//     * 加载图片
//     *
//     * @param context
//     * @param config
//     * @param <T>
//     */
//    fun <T : ImageLoaderConfig> loadImage(context: Context, config: T) {
//        Preconditions.checkNotNull(mLoaderStrategy, "Please implement ILoaderStrategy")
//        mLoaderStrategy?.loadImage(context, config)?: error()
//    }
//
//
//    /**
//     * 加载网络图片，并通过imageListener回调返回Bitmap，回调在主线程
//     */
//    fun getBitmap(context: Context, url: String, loaderCallback: ImageLoaderCallback<Bitmap>) {
//        mLoaderStrategy?.getBitmap(context, url, loaderCallback) ?: error()
//    }
//
//    /**
//     * 下载网络图片，并通过imageListener回调返回下载得到的图片文件，回调在后台线程
//     */
//    fun downLoadImage(context: Context, url: String, saveFile: File, loaderCallback: ImageLoaderCallback<File>) {
//        mLoaderStrategy?.downLoadImage(context, url, saveFile, loaderCallback) ?: error()
//    }
//
//    /**
//     * 清除磁盘缓存(子线程)
//     */
//    fun clearDiskCache(ctx: Context) {
//        mLoaderStrategy?.clearDiskCache(ctx) ?: error()
//    }
//
//    /**
//     * 清除内存缓存(UI线程)
//     */
//    fun clearMemoryCache(ctx: Context) {
//        mLoaderStrategy?.clearMemoryCache(ctx) ?: error()
//    }
//
//    /**
//     * 获取图片缓存大小
//     */
//    fun getCacheSize(ctx: Context) : String {
//        return mLoaderStrategy?.getCacheSize(ctx) ?: error()
//    }
//
//
//    private fun error(): Nothing =
//        throw IllegalArgumentException("You must provide an image loader strategy first!")
//
//}