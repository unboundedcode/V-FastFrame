package com.vension.fastframe.module_course.ui.adapter.section;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.vension.fastframe.module_course.R;
import com.zhouwei.mzbanner.holder.MZViewHolder;
import kv.vension.fastframe.utils.DensityUtil;

/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/26 10:17
 * 更 新：2019/7/26 10:17
 * 描 述：
 * ========================================================
 */

public class BannerViewHolder implements MZViewHolder<String> {
    private ImageView mImageView;

    @Override
    public View createView(Context context) {
        // 返回页面布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_recy_section_banner, null);
        mImageView = view.findViewById(R.id.banner_image);
        return view;
    }

    @Override
    public void onBind(Context context, int position, String data) {
        // 数据绑定
        //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(DensityUtil.INSTANCE.dp2px(10));
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);

        Glide.with(context)
                .load(data)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(options)
                .dontAnimate()
                .into(mImageView);
    }
}
