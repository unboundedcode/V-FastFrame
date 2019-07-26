package com.vension.fastframe.module_course.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.fastframe.module_course.R
import com.vension.fastframe.module_course.bean.DiscoveryCommentBean
import kotlinx.android.synthetic.main.choose_left_item.view.*
import kv.vension.vframe.VFrame.getColor

/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/26 14:09
 * 更 新：2019/7/26 14:09
 * 描 述：选择阶段-left列表
 * ========================================================
 */

class ChoosePhaseAdapter(layoutId: Int, mData: List<DiscoveryCommentBean.Stages.SubTag>?) : BaseQuickAdapter<DiscoveryCommentBean.Stages.SubTag, BaseViewHolder>(layoutId, mData) {
    var selectedPosition: Int = -1

    override fun convert(helper: BaseViewHolder, item: DiscoveryCommentBean.Stages.SubTag) {
        helper.apply {
            item.let {
                setText(R.id.left_text, item.tagName)
                addOnClickListener(R.id.left_item)
                if (adapterPosition == selectedPosition) {
                    itemView.left_text.setTextColor(getColor(R.color.tab_textSelectColor))
                    itemView.left_item.setBackgroundColor(getColor(R.color.choose_left_bg))
                } else {
                    // 默认 不设置颜色
                    itemView.left_text.setTextColor(getColor(R.color.tab_textUnSelectColor))
                    // 默认背景
                    itemView.left_item.setBackgroundColor(getColor(R.color.choose_left_bg_UnSelect))
                }
            }
        }
    }


}