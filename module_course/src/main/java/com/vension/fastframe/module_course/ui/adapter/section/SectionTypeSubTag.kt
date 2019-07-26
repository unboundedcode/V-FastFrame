package com.vension.fastframe.module_course.ui.adapter.section

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.vension.fastframe.module_course.R
import com.vension.fastframe.module_course.bean.VerticalBean
import com.vension.fastframe.module_course.widget.section.StatelessSection
import com.vension.fastframe.module_course.widget.section.ViewHolder

/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/26 12:10
 * 更 新：2019/7/26 12:10
 * 描 述：标签顶部Tab-section
 * ========================================================
 */

class SectionTypeSubTag(private val list: List<VerticalBean.SubTag>?) : StatelessSection<Nothing>(R.layout.layout_item_section_type_subtag, R.layout.layout_section_empty) {

    override fun onBindHeaderViewHolder(holder: ViewHolder) {
        holder.let {
            val recyclerView = it.getView<RecyclerView>(R.id.recycler)
            recyclerView.setHasFixedSize(true)
            val mLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
            recyclerView.layoutManager = mLayoutManager
            recyclerView.adapter = SectionTypeSubTagAdapter(list)
        }
    }
}