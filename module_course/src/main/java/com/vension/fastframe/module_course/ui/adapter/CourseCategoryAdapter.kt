package com.vension.fastframe.module_course.ui.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.fastframe.module_course.R
import com.vension.fastframe.module_course.bean.VideoBean

/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/25 15:38
 * 更 新：2019/7/25 15:38
 * 描 述：
 * ========================================================
 */

class CourseCategoryAdapter : BaseQuickAdapter<VideoBean, BaseViewHolder>(R.layout.item_recy_wk_course_video) {

    override fun convert(helper: BaseViewHolder, item: VideoBean) {
        helper.setText(R.id.tv_title, item.title)
        Glide.with(mContext)
                .load(item.img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(helper.getView(R.id.iv_preview))
    }

}