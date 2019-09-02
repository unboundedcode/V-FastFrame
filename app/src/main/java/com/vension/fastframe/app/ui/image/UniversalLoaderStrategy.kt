package com.vension.fastframe.app.ui.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import androidx.core.util.Preconditions
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.assist.ImageScaleType
import com.nostra13.universalimageloader.core.assist.QueueProcessingType
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer
import com.nostra13.universalimageloader.core.download.BaseImageDownloader
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener
import kv.vension.fastframe.VFrame
import kv.vension.fastframe.cache.FileCache
import kv.vension.fastframe.image.ILoaderStrategy
import kv.vension.fastframe.image.ImageLoaderCallback
import kv.vension.fastframe.utils.BitmapUtil
import kv.vension.fastframe.utils.FileUtil
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton


/**
 * ========================================================================
 * @author: Created by Vension on 2019/8/29 17:01.
 * @email:  vensionHu@qq.com
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

@Singleton
class UniversalLoaderStrategy : ILoaderStrategy<UniversalConfigImpl> {

    @Inject
    constructor()

    private val universalLoader by lazy {
        val configuration = ImageLoaderConfiguration.createDefault(VFrame.getContext())
        val loader = ImageLoader.getInstance()
        loader.init(configuration)
        loader.init(builder.build())
        return@lazy loader
    }

    /**
     *  ImageLoaderConfiguration是针对ImageLoader这个框架的全局配置。
     *  包括线程池线程数量，磁盘缓存大小，内存缓存大小，缓存文件数量，缓存文件路径等配置。
     *  它使用了 建造者模式 ， 还提供了一个静态函数 createDefault(Context context) 来创建一个最基本的配置。
     */
    private val builder by lazy {
        ImageLoaderConfiguration.Builder(VFrame.getContext())
            .threadPoolSize(5)// 线程池大小
            .threadPriority(Thread.NORM_PRIORITY - 2)// 设置线程优先级
            .denyCacheImageMultipleSizesInMemory()// 不允许在内存中缓存同一张图片的多个尺寸
            .tasksProcessingOrder(QueueProcessingType.LIFO) // 设置处理队列的类型，包括LIFO， FIFO
            .memoryCache( LruMemoryCache(3 * 1024 * 1024))// 内存缓存策略
            .memoryCacheSize(5 * 1024 * 1024)  // 内存缓存大小
            .memoryCacheExtraOptions(480, 800) // 内存缓存中每个图片的最大宽高
            .memoryCacheSizePercentage(50)// 内存缓存占总内存的百分比
            .diskCache( UnlimitedDiskCache(FileUtil.getFile(FileCache.getCacheImagePath()))) // 设置磁盘缓存策略
            .diskCacheSize(50 * 1024 * 1024) // 设置磁盘缓存的大小
            .diskCacheFileCount(50) // 磁盘缓存文件数量
            .diskCacheFileNameGenerator( Md5FileNameGenerator())// 磁盘缓存时图片名称加密方式
            .imageDownloader( BaseImageDownloader(VFrame.getContext())) // 图片下载器
            .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
            .writeDebugLogs()// 打印日志
    }

    override fun loadImage(context: Context, url: Any, imageView: ImageView) {
        doLoad(context,UniversalConfigImpl.builder().url(url).imageView(imageView).build())
    }

    override fun loadImage(context: Context, config: UniversalConfigImpl) {
        doLoad(context, config)
    }

    override fun getBitmap(context: Context, url: String, imageListener: ImageLoaderCallback<Bitmap>) {
        try {
            universalLoader.loadImage(url, object : ImageLoadingListener{
                override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                    imageListener.onSuccess(loadedImage!!)
                }

                override fun onLoadingStarted(imageUri: String?, view: View?) {
                }

                override fun onLoadingCancelled(imageUri: String?, view: View?) {
                    imageListener.onFailed("getBitmap fail")
                }

                override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {
                    imageListener.onFailed("getBitmap fail")
                }

            })
        }catch (e:Exception){
            imageListener.onFailed("getBitmap fail")
        }
    }

    override fun downLoadImage(context: Context, url: String, saveFile: File, imageListener: ImageLoaderCallback<File>) {
        try {
            universalLoader.loadImage(url, object : ImageLoadingListener{
                override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                    if(BitmapUtil.saveBitmapToFile(loadedImage!!,saveFile)){
                        imageListener.onSuccess(saveFile)//回调在后台线程
                    }else{
                        imageListener.onFailed("downLoadImage fail")//回调在后台线程
                    }
                }

                override fun onLoadingStarted(imageUri: String?, view: View?) {
                }

                override fun onLoadingCancelled(imageUri: String?, view: View?) {
                    imageListener.onFailed("downLoadImage fail")
                }

                override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {
                    imageListener.onFailed("downLoadImage fail")
                }

            })

        } catch (exception:Exception ) {
            exception.printStackTrace()
            imageListener.onFailed("downLoadImage fail")
        }
    }

    override fun clearMemoryCache(context: Context) {
        universalLoader.clearMemoryCache()
    }

    override fun clearDiskCache(context: Context) {
        universalLoader.clearDiskCache()
    }

    override fun getCacheSize(context: Context): String {
        return FileUtil.formatFileSize(FileUtil.getFileSize(File(FileCache.getCacheImagePath())))
    }

    /**
     * ImageLoadingListener 在加载图片的时候设置监听器可以监听到开始，失败，成功，取消等回调！
     * 也可以使用它的实现类 SimpleImageLoadingListener ， 这样可以在代码中少看到一些不需要的回调！
     */


    /**
     * 滚动时暂定加载
     * 当使用ListView，RecyclerView， GridView的时候，滚动屏幕时暂停图片加载可减少界面卡顿！
     * listView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
     * recyclerView.addOnScrollListener(new RVPauseOnScrollListener(ImageLoader.getInstance(), true, true));
     * gridView.setOnScrollListener(new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling));
     */
    private fun doLoad(context: Context, configImpl: UniversalConfigImpl) {
        Preconditions.checkNotNull(context, "Context is required")
        Preconditions.checkNotNull(configImpl, "UniversalConfigImpl is required")
        Preconditions.checkNotNull(configImpl.imageView, "ImageView is required")

        /**
         * ImageLoader 是UIL初始化和加载显示图片的关键类！通过 单例模式 进行初始化！
         * displayImage() 异步加载图片并显示
         * loadImage() 异步加载图片
         * loadImageSync() 同步加载图片
         */

        /**
         * DisplayImageOptions 在配置 ImageLoaderConfiguration 的时候就可以统一配置图片的参数！
         * 但是我们也可以针对不同的图片做不同的配置！同样使用了 建造者模式 ！！！
         */
        val imageOptions = DisplayImageOptions.Builder()
        // 将要开始加载时是否需要替换成onLoading图片
        imageOptions.resetViewBeforeLoading(true)
        imageOptions .delayBeforeLoading(1000) // 加载延迟时间
//        imageOptions.preProcessor(xxx) // 图片加入缓存之前的处理
//        imageOptions.postProcessor(xxx) // 图片在显示之前的处理
        imageOptions.decodingOptions(BitmapFactory.Options()) // 解码参数
        imageOptions.considerExifParams(true) // 是否考虑Exif参数
        imageOptions.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // 缩放类型(true)
        imageOptions.bitmapConfig(Bitmap.Config.RGB_565) // bitmap模式
        configImpl?.let {config->

            if (config.isCircle) {
                if(config.borderWidth > 0f && config.borderColor > 0){
                    imageOptions.displayer( CircleBitmapDisplayer(config.borderColor,config.borderWidth))
                }else{
                    imageOptions.displayer( CircleBitmapDisplayer())
                }
            }

            // 设置图片显示形式(圆角 or 渐变等)
            if (config.roundRadius > 0) {
                imageOptions.displayer( RoundedBitmapDisplayer(20))

            }

            // 需要缓存在内存中
            if( config.isCacheInMemory ){
                imageOptions.cacheInMemory(true)
            }
            if( config.isCacheOnDisk ){
                imageOptions.cacheOnDisk(true) // 需要缓存到磁盘中(true)
            }

            // 加载过程中的显示图片
            imageOptions.showImageOnLoading(if(config.placeholder > 0 ) config.placeholder else com.vension.fastframe.app.R.color.placeholder_color)
            //  加载失败显示的图片
            imageOptions.showImageOnFail(if(config.errorPic > 0 ) config.errorPic else kv.vension.fastframe.R.color.placeholder_color)
            //路径为空时显示的图片
            imageOptions.showImageForEmptyUri(if(config.fallback > 0 ) config.fallback else kv.vension.fastframe.R.color.placeholder_color)
            imageOptions.showImageForEmptyUri(if(config.fallback > 0 ) config.fallback else kv.vension.fastframe.R.color.placeholder_color)
        }

        //最后异步加载图片
        universalLoader.displayImage(configImpl.url.toString(), configImpl.imageView, imageOptions.build())
    }


}


