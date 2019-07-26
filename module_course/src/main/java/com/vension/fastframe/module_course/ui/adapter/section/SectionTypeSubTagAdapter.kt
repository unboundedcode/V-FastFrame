package com.vension.fastframe.module_course.ui.adapter.section

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.fastframe.module_course.R
import com.vension.fastframe.module_course.bean.VerticalBean

/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/26 12:12
 * 更 新：2019/7/26 12:12
 * 描 述：标签顶部tab
 * ========================================================
 */

class SectionTypeSubTagAdapter(list: List<VerticalBean.SubTag>?) : BaseQuickAdapter<VerticalBean.SubTag, BaseViewHolder>(
    R.layout.item_section_type_subtag, list) {
    override fun convert(helper: BaseViewHolder, item: VerticalBean.SubTag) {
        helper.apply {
            item.let {
                setText(R.id.tv_title, item.name)
                Glide.with(mContext)
                        .load(item.icon)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(helper.getView(R.id.iv_icon))
            }
        }
    }

}