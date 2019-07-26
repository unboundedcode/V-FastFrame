package com.vension.fastframe.module_course.ui.adapter.section

import com.vension.fastframe.module_course.R
import com.vension.fastframe.module_course.bean.VerticalBean
import com.vension.fastframe.module_course.widget.section.StatelessSection
import com.vension.fastframe.module_course.widget.section.ViewHolder
import com.zhouwei.mzbanner.MZBannerView

/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/26 12:08
 * 更 新：2019/7/26 12:08
 * 描 述：标签轮播section
 * ========================================================
 */

class SectionTypeBanner(private val list: List<VerticalBean.Banner>?) : StatelessSection<Nothing>(R.layout.layout_banner, R.layout.layout_section_empty) {
    override fun onBindHeaderViewHolder(holder: ViewHolder) {
        val urls = list?.map { it.img }
        val mMZBanner = holder.itemView.findViewById(R.id.banner) as MZBannerView<String>
        // 设置数据
        mMZBanner.setPages(urls) {
            BannerViewHolder()
        }
        mMZBanner.start()
    }
}