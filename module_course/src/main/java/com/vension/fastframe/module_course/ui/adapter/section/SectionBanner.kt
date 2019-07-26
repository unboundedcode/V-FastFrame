package com.vension.fastframe.module_course.ui.adapter.section

import com.vension.fastframe.module_course.R
import com.vension.fastframe.module_course.bean.SelectionBean
import com.vension.fastframe.module_course.widget.section.StatelessSection
import com.vension.fastframe.module_course.widget.section.ViewHolder
import com.zhouwei.mzbanner.MZBannerView


/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/26 10:17
 * 更 新：2019/7/26 10:17
 * 描 述：首页精选轮播section
 * ========================================================
 */

class SectionBanner(private val list: List<SelectionBean.Head>?) : StatelessSection<Nothing>(R.layout.layout_banner, R.layout.layout_section_empty) {

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