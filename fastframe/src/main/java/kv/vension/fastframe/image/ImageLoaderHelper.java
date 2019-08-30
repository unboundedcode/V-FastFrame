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
package kv.vension.fastframe.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.util.Preconditions;

import java.io.File;

/**
 * @author Created by：Fanson
 * Created Time: 2018/10/19 14:32
 * Describe：
 */
/**
 * ========================================================================
 * 作 者：Vension
 * 主 页：Github: https://github.com/Vension
 * 日 期：2019/8/26 12:16
 * ______       _____ _______ /\/|_____ _____            __  __ ______
 * |  ____/\    / ____|__   __|/\/  ____|  __ \     /\   |  \/  |  ____|
 * | |__ /  \  | (___    | |     | |__  | |__) |   /  \  | \  / | |__
 * |  __/ /\ \  \___ \   | |     |  __| |  _  /   / /\ \ | |\/| |  __|
 * | | / ____ \ ____) |  | |     | |    | | \ \  / ____ \| |  | | |____
 * |_|/_/    \_\_____/   |_|     |_|    |_|  \_\/_/    \_\_|  |_|______|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * 描述：图片加载框架的代理类，提供对外接口。
 * [ImageLoaderHelper] 使用策略模式和建造者模式,可以动态切换图片请求框架
 *开发者只需要关心ILoaderStrategy + ImageLoaderConfig
 * @use
 * 1、初始化
 * ImageLoaderHelper.INSTANCE.setImageLoaderStrategy(GlideLoaderStrategy())
 * 2、普通下载图片
 * ImageLoaderHelper.INSTANCE.loadImage(mContext,url,iv)
 * ========================================================================
 */
public final class ImageLoaderHelper {

    private static ILoaderStrategy mStrategy;
    private static volatile ImageLoaderHelper sInstance;

    private ImageLoaderHelper() {
    }

    public static ImageLoaderHelper getInstance(){
        if(sInstance == null){
            synchronized (ImageLoaderHelper.class){
                if(sInstance == null){
                    sInstance = new ImageLoaderHelper();
                }
            }
        }
        return sInstance;
    }

    /**
     * 可在运行时随意切换 {@link ILoaderStrategy}
     *
     * @param strategy
     */
    public void setImageLoaderStrategy(ILoaderStrategy strategy) {
        Preconditions.checkNotNull(strategy, "strategy == null");
        this.mStrategy = strategy;
    }

    @Nullable
    public ILoaderStrategy getImageLoaderStrategy() {
        return mStrategy;
    }


    public void loadImage(Context ctx, Object url, ImageView imageView){
        Preconditions.checkNotNull(mStrategy, "Please implement ILoaderStrategy");
        this.mStrategy.loadImage(ctx, url,imageView);
    }
    /**
     * 加载图片
     *
     * @param context
     * @param config
     * @param <T>
     */
    public <T extends ImageLoaderConfig> void loadImage(Context context, T config) {
        Preconditions.checkNotNull(mStrategy, "Please implement ILoaderStrategy");
        mStrategy.loadImage(context, config);
    }


    /**
     * 加载网络图片，并通过imageListener回调返回Bitmap，回调在主线程
     */
    public void getBitmap(Context context,String url, ImageLoaderCallback<Bitmap> loaderCallback){
        mStrategy.getBitmap(context,url,loaderCallback);
    }

    /**
     *  下载网络图片，并通过imageListener回调返回下载得到的图片文件，回调在后台线程
     */
    public void downLoadImage(Context context,String url, File saveFile,ImageLoaderCallback<File> loaderCallback){
        mStrategy.downLoadImage(context,url,saveFile,loaderCallback);
    }

    /**
     * 清理内存缓存
     */
    public void clearMemoryCache(Context context){
        mStrategy.clearMemoryCache(context);
    }

    /**
     * 清理磁盘缓存
     */
    public void clearDiskCache(Context context){
        mStrategy.clearDiskCache(context);
    }

    /**
     * get size(MB) of image cache.
     */
    public String getCacheSize(Context context){
        return mStrategy.getCacheSize(context);
    }

}
