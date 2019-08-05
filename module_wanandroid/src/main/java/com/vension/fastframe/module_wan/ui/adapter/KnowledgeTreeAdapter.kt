package com.vension.fastframe.module_wan.ui.adapter

import android.content.Context
import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.fastframe.module_wan.R
import com.vension.fastframe.module_wan.bean.KnowledgeTreeBody

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/7 15:43
 * 描  述：菜单-体系适配器
 * ========================================================
 */

class KnowledgeTreeAdapter(private val context: Context?) : BaseQuickAdapter<KnowledgeTreeBody, BaseViewHolder>(R.layout.item_recy_knowledge_tree) {

    override fun convert(helper: BaseViewHolder, item: KnowledgeTreeBody?) {
        helper?.setText(R.id.title_first, item?.name)
        item?.children.let {
            helper?.setText(R.id.title_second,
                    it?.joinToString("    ", transform = { child ->
                        Html.fromHtml(child.name)
                    })
            )

        }
    }

}