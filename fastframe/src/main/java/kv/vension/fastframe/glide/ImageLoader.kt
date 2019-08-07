package kv.vension.fastframe.glide

import android.content.Context
import android.net.Uri
import android.preference.PreferenceManager
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Priority
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.request.RequestOptions
import kv.vension.fastframe.R
import kv.vension.fastframe.VFrame
import kv.vension.fastframe.utils.NetWorkUtil


/**
 * ========================================================
 * @author: Created by Vension on 2018/12/4 15:25.
 * @email:  250685***4@qq.com
 * @desc:   图片加载工具
 * ========================================================
 */
object ImageLoader {
    private val setting = PreferenceManager.getDefaultSharedPreferences(VFrame.getContext())

    val ANDROID_RESOURCE = "android.resource://"
    val FILE = "file://"
    val SEPARATOR = "/"

    /**
     * 1.开启无图模式 2.非WiFi环境 不加载图片
     */
    private val isLoadImage = !setting.getBoolean("switch_noPhotoMode", false) || NetWorkUtil.isWifi()

    fun resId2Uri(context: Context,@DrawableRes resId: Int): String {
        return Uri.parse(ANDROID_RESOURCE + context.packageName + SEPARATOR + resId).toString()
    }

    /**
     *  加载本地资源
     */
    fun loadResource(context: Context,@DrawableRes resId:Int,imageView: ImageView){
        loadImage(context,imageView, resId2Uri(context,resId))
    }
    fun loadResource(context: Context,@DrawableRes resId:Int,imageView: ImageView,transformation : BitmapTransformation){
        loadImage(context,imageView, resId2Uri(context,resId),transformation)
    }


    /**
     *  加载网络图片
     */
    fun loadImage(context: Context, imageView: ImageView, uri: String) {
        loadImage(context,imageView,uri,0)
    }
    fun loadImage(context: Context, imageView: ImageView, uri: String, placeholderId : Int) {
        if (isLoadImage) {
            GlideApp
                .with(context)
                .load(uri)
                .centerCrop()
                .priority(Priority.HIGH)
                .placeholder(if(placeholderId > 0) placeholderId else R.color.placeholder_color)
                .into(imageView)
        }
    }
    fun loadImage(context: Context, imageView: ImageView, uri: String,transformation : BitmapTransformation) {
        loadImage(context,imageView,uri,0,transformation)
    }
    fun loadImage(context: Context, imageView: ImageView, uri: String,placeholderId : Int,transformation : BitmapTransformation) {
        if (isLoadImage) {
            GlideApp
                .with(context)
                .load(uri)
                .centerCrop()
                .priority(Priority.HIGH)
                .placeholder(if(placeholderId > 0) placeholderId else R.color.placeholder_color)
                .apply( RequestOptions.bitmapTransform(transformation))
                .into(imageView)
        }
    }


    /**
     *  加载网络图片缩略图
     */
    fun loadThumbnail(context: Context,imageView: ImageView, resize: Int, placeholderId: Int,  uri: String) {
        GlideApp
            .with(context)
            .asBitmap()
            .load(uri)
            .centerCrop()
            .override(resize, resize)
            .placeholder(if(placeholderId > 0) placeholderId else R.color.placeholder_color)
            .into(imageView)
    }
    /**
     *  加载网络图片缩略图
     */
    fun loadThumbnail(context: Context,imageView: ImageView, resize: Int, placeholderId: Int,  uri: String,transformation : BitmapTransformation) {
        GlideApp
            .with(context)
            .asBitmap()
            .load(uri)
            .centerCrop()
            .override(resize, resize)
            .placeholder(if(placeholderId > 0) placeholderId else R.color.placeholder_color)
            .apply( RequestOptions.bitmapTransform(transformation))
            .into(imageView)
    }


    /**
     *  加载网络Gif缩略图
     */
    fun loadGifThumbnail(context: Context, resize: Int, placeholderId : Int, imageView: ImageView, uri: String) {
            GlideApp
                .with(context)
                .asBitmap()
                .load(uri)
                .centerCrop()
                .override(resize, resize)
                .placeholder(if(placeholderId > 0) placeholderId else R.color.placeholder_color)
                .into(imageView)
    }


    /**
     *  加载网络Gif
     */
    fun loadGifImage(context: Context, imageView: ImageView, uri: String) {
        if (isLoadImage) {
            GlideApp
                .with(context)
                .asGif()
                .load(uri)
                .centerCrop()
                .priority(Priority.HIGH)
                .placeholder(R.color.placeholder_color)
                .into(imageView)
        }
    }

    fun supportAnimatedGif(): Boolean {
        return true
    }

}