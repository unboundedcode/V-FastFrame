package kv.vension.fastframe.image

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import java.io.File

/**
 * ========================================================================
 * 作 者：Vension
 * 主 页：Github: https://github.com/Vension
 * 日 期：2019/8/26 12:10
 *   ______       _____ _______ /\/|_____ _____            __  __ ______
 *  |  ____/\    / ____|__   __|/\/  ____|  __ \     /\   |  \/  |  ____|
 *  | |__ /  \  | (___    | |     | |__  | |__) |   /  \  | \  / | |__
 *  |  __/ /\ \  \___ \   | |     |  __| |  _  /   / /\ \ | |\/| |  __|
 *  | | / ____ \ ____) |  | |     | |    | | \ \  / ____ \| |  | | |____
 *  |_|/_/    \_\_____/   |_|     |_|    |_|  \_\/_/    \_\_|  |_|______|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * 描述：加载图片框架的策略接口，实现 {@link ILoaderStrategy}
 * 并通过 {@link ImageLoaderHelper#setImageLoaderStrategy(ILoaderStrategy)} 配置后,才可进行图片请求
 * ========================================================================
 */
interface ILoaderStrategy<in T : ImageLoaderConfig> {

    /**
     * 加载图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    fun loadImage(context: Context, url: Any, imageView: ImageView)

    /**
     * 加载图片
     *
     * @param context
     * @param config 图片加载配置信息
     */
    fun loadImage(context: Context, config: T)

    /**
     * 加载网络图片，并通过imageListener回调返回Bitmap，回调在主线程
     */
    fun getBitmap(context: Context,url: String, imageListener: ImageLoaderCallback<Bitmap>)

    /**
     *  下载网络图片，并通过imageListener回调返回下载得到的图片文件，回调在后台线程
     */
    fun downLoadImage(context: Context, url: String, saveFile: File, imageListener: ImageLoaderCallback<File>)

    /**
     * 清理内存缓存
     */
    fun clearMemoryCache(context: Context)

    /**
     * 清理磁盘缓存
     */
    fun clearDiskCache(context: Context)

    /**
     * get size(MB) of image cache.
     */
    fun getCacheSize(context: Context) : String

}