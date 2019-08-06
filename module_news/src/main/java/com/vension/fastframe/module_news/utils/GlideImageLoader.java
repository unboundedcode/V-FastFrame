package com.vension.fastframe.module_news.utils;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vension.fastframe.module_news.R;
import com.youth.banner.loader.ImageLoader;
import kv.vension.fastframe.glide.GlideApp;

/**
 * author：JSYL-DCL on 2017/9/21
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        GlideApp.with(context.getApplicationContext()).load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.place_image_default)
                .error(R.drawable.place_image_default)
                .into(imageView);
    }

}