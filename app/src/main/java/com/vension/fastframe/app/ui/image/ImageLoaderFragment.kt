package com.vension.fastframe.app.ui.image

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import com.vension.fastframe.app.R
import kotlinx.android.synthetic.main.fragment_tab_image.*
import kv.vension.fastframe.VFrame.getAssets
import kv.vension.fastframe.core.AbsCompatFragment
import kv.vension.fastframe.ext.showToast
import kv.vension.fastframe.image.ImageLoaderCallback
import kv.vension.fastframe.image.ImageLoaderHelper
import kv.vension.fastframe.image.glide.GlideConfigImpl
import kv.vension.fastframe.image.glide.GlideLoaderStrategy
import kv.vension.fastframe.utils.FileUtil
import org.jetbrains.anko.support.v4.runOnUiThread
import java.io.File
import java.io.FileOutputStream



/**
 * ========================================================================
 * @author: Created by Vension on 2019/8/23 17:32.
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
 * @desc: happy code -> 图片加载模块
 * ========================================================================
 */
class ImageLoaderFragment : AbsCompatFragment() {

    private var useUniversalLoader = false
    private val picUrl = "http://guolin.tech/book.png"
    private val picUrl2 = "http://static.aiyanxueonline.com/tmp/wxd32f1d99a874af10.o6zAJs46gNQW3Mz32eLJN34YWHpM.igiYgFLqOEmXc72ff925a26dc18be14a4d962963ec1d.png"

    companion object{
        fun newInstance() : ImageLoaderFragment {
            return ImageLoaderFragment()
        }
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_tab_image
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        initFile()//复制assets中的图片到本地以便演示加载本地图片

        tv_cacheSize.text = ImageLoaderHelper.getInstance().getCacheSize(mContext)
        btn_net.setOnClickListener(this)
        btn_res.setOnClickListener(this)
        btn_assets.setOnClickListener(this)
        btn_local.setOnClickListener(this)

        btn_circle.setOnClickListener(this)
        btn_round.setOnClickListener(this)
        btn_blur.setOnClickListener(this)
        btn_gray.setOnClickListener(this)
        btn_circle_border.setOnClickListener(this)

        btn_bitmap.setOnClickListener(this)
        btn_download.setOnClickListener(this)
        btn_memory_cache.setOnClickListener(this)
        btn_disk_cache.setOnClickListener(this)
        btn_universalLoader.setOnClickListener(this)
    }


    fun initFile() {
        //复制assets中的图片到本地
        Thread(Runnable {
            try {
                val file =
                    FileUtil.getFile(FileUtil.getExternalCacheDir(mContext), "image_fast_frame.png")
                val `in` = getAssets().open("image_fast_frame.png")
                val out = FileOutputStream(file)
                val buf = ByteArray(1024)
                while (`in`.read(buf) != -1) {
                    out.write(buf, 0, buf.size)
                }
                `in`.close()
                out.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v?.id){
            R.id.btn_net -> {//加载网络图片
                ImageLoaderHelper.getInstance().loadImage(mContext,picUrl,iv_common)
            }
            R.id.btn_res -> { //加载Res资源图片
                if(useUniversalLoader){
                    ImageLoaderHelper.getInstance().loadImage(mContext, "drawable://" + com.vension.fastframe.app.R.drawable.img_advertisment,iv_common)
                }else{
                    ImageLoaderHelper.getInstance().loadImage(mContext, com.vension.fastframe.app.R.drawable.img_advertisment,iv_common)
                }
            }
            R.id.btn_assets -> {//加载assets资源图片
                if(useUniversalLoader){
                    ImageLoaderHelper.getInstance().loadImage(mContext, "assets://image_fast_frame.png",iv_common)
                }else{
                    ImageLoaderHelper.getInstance().loadImage(mContext,"file:///android_asset/image_fast_frame.png",iv_common)
                }
            }
            R.id.btn_local -> { //加载本地图片
                val file = FileUtil.getFile(FileUtil.getExternalCacheDir(mContext), "image_fast_frame.png")
                if(useUniversalLoader){
                    ImageLoaderHelper.getInstance().loadImage(mContext,"file:///" + file!!.absolutePath,iv_common)
                }else{
                    ImageLoaderHelper.getInstance().loadImage(mContext,file!!, iv_common)
                }
            }
            R.id.btn_circle -> { //指定图片加载为圆形
                if(useUniversalLoader){
                    ImageLoaderHelper.getInstance().loadImage(mContext,
                        UniversalConfigImpl.builder()
                            .url(picUrl2)
                            .imageView(iv_option)
                            .isCircle(true)
                            .build())
                }else{
                    ImageLoaderHelper.getInstance().loadImage(mContext,
                        GlideConfigImpl.builder()
                            .url(picUrl2)
                            .imageView(iv_option)
                            .isCircle(true)
                            .build())
                }
            }
            R.id.btn_round -> { //指定图片加载为圆角
                if(useUniversalLoader){
                    ImageLoaderHelper.getInstance().loadImage(mContext,
                        UniversalConfigImpl.builder()
                            .url(picUrl2)
                            .imageView(iv_option)
                            .roundRadius(12)
                            .build())
                }else{
                    ImageLoaderHelper.getInstance().loadImage(mContext,
                        GlideConfigImpl.builder()
                            .url(picUrl2)
                            .imageView(iv_option)
                            .roundRadius(12)
                            .build())
                }
            }
            R.id.btn_blur -> { //指定图片加载为模糊效果
                ImageLoaderHelper.getInstance().loadImage(mContext,
                    GlideConfigImpl.builder()
                        .url(picUrl)
                        .imageView(iv_option)
                        .blurValue(10)
                        .build())
            }
            R.id.btn_gray -> { //指定图片加载为灰白效果
                ImageLoaderHelper.getInstance().loadImage(mContext,
                    GlideConfigImpl.builder()
                        .url(picUrl)
                        .imageView(iv_option)
                        .isGray(true)
                        .build())
            }
            R.id.btn_circle_border -> { //指定图片边框的粗细以及颜色（目前边框仅适用于圆形条件下）
                if(useUniversalLoader){
                    ImageLoaderHelper.getInstance().loadImage(mContext,
                        UniversalConfigImpl.builder()
                            .url(picUrl2)
                            .imageView(iv_option)
                            .isCircle(true)
                            .borderWidth(4.0f)
                            .borderColor(R.color.color_red)
                            .build())
                }else{
                    ImageLoaderHelper.getInstance().loadImage(mContext,
                        GlideConfigImpl.builder()
                            .url(picUrl2)
                            .imageView(iv_option)
                            .isCircle(true)
                            .borderWidth(4)
                            .borderColor(R.color.color_red)
                            .build())
                }

            }
            R.id.btn_bitmap -> { //获取网络图片的Bitmap对象
                ImageLoaderHelper.getInstance().getBitmap(mContext, picUrl2, object :
                    ImageLoaderCallback<Bitmap> {
                    override fun onFailed(msg: String) {
                        showToast(msg)
                    }

                    override fun onSuccess(result: Bitmap) {
                        iv_bitmap.setImageBitmap(result)
                    }
                })
            }
            R.id.btn_download -> { //下载图片到本地指定位置
                val fileDownload = FileUtil.getFile(FileUtil.getExternalCacheDir(mContext), "image_download.jpg")
                ImageLoaderHelper.getInstance().downLoadImage( mContext,picUrl2, fileDownload!!, object : ImageLoaderCallback<File> {
                    override fun onSuccess(result: File) {
                        runOnUiThread {
                            if(useUniversalLoader){
                                // String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
                                ImageLoaderHelper.getInstance().loadImage(mContext,"file:///" + result.absolutePath,iv_download)
                            }else{
                                ImageLoaderHelper.getInstance().loadImage(mContext,result, iv_download)
                            }
                        }
                    }

                    override fun onFailed(msg: String) {
                        showToast(msg)
                    }
                })
            }
            R.id.btn_memory_cache -> { //清空内存缓存
                ImageLoaderHelper.getInstance().clearMemoryCache(mContext)
                setNullImageViews()
            }
            R.id.btn_disk_cache -> { //清空磁盘缓存
                ImageLoaderHelper.getInstance().clearDiskCache(mContext)
                setNullImageViews()
            }
            R.id.btn_universalLoader -> { //使用universalLoader
                useUniversalLoader = !useUniversalLoader
                setNullImageViews()
                ImageLoaderHelper.getInstance().imageLoaderStrategy = if(useUniversalLoader) UniversalLoaderStrategy() else GlideLoaderStrategy()
                tv_cacheSize.text = "0.0KB"
                btn_universalLoader.text =  if(useUniversalLoader) "使用Glide" else "使用universalLoader"
                btn_blur.visibility = if(useUniversalLoader) View.GONE else View.VISIBLE
                btn_gray.visibility = if(useUniversalLoader) View.GONE else View.VISIBLE
            }
        }
        tv_cacheSize.text = ImageLoaderHelper.getInstance().getCacheSize(mContext)
    }

    private fun setNullImageViews() {
        iv_common.setImageDrawable(null)
        iv_option.setImageDrawable(null)
        iv_bitmap.setImageDrawable(null)
        iv_download.setImageDrawable(null)
    }


    override fun lazyLoadData() {
    }
}