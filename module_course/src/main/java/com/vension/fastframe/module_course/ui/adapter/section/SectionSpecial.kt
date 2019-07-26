package com.vension.fastframe.module_course.ui.adapter.section

import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.vension.fastframe.module_course.R
import com.vension.fastframe.module_course.bean.SelectionBean
import com.vension.fastframe.module_course.ui.fragment.SectionDetailFragment
import com.vension.fastframe.module_course.widget.section.StatelessSection
import com.vension.fastframe.module_course.widget.section.ViewHolder
import kv.vension.vframe.core.AbsCompatFragment
import kv.vension.vframe.utils.DensityUtil


/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/26 10:23
 * 更 新：2019/7/26 10:23
 * 描 述：首页-名师推荐-section
 * ========================================================
 */

class SectionSpecial(val fragment:AbsCompatFragment,list: List<SelectionBean.Zhuanlan>?) : StatelessSection<SelectionBean.Zhuanlan>(R.layout.layout_item_section_head, R.layout.layout_item_special_body, list) {

    override fun convert(holder: ViewHolder, zhuanlan: SelectionBean.Zhuanlan, position: Int) {
        holder.apply {
            zhuanlan.let {
                if (position != 0) getView<ConstraintLayout>(R.id.item).setBackgroundResource(R.drawable.ic_bottom_lines)

                setText(R.id.special_title, it.title)
                setText(R.id.special_teacherName, it.teacherName)
                setText(R.id.special_teacherTag, it.teacherTag)
                setText(R.id.special_content, it.introduction)

                //设置图片圆角角度
                val roundedCorners = RoundedCorners(DensityUtil.dp2px(10))
                //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
                val options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300)

                Glide.with(mContext)
                    .load(it.img)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(options)
                    .into(getView(R.id.special_imgView))

                itemView.setOnClickListener {
                    fragment.startProxyActivity(SectionDetailFragment::class.java)
                }
            }
        }
    }


    override fun onBindHeaderViewHolder(holder: ViewHolder?) {
        holder?.setText(R.id.headTitle, R.string.head_zhuanlan)?.setVisible(R.id.headMore, false)
    }
}