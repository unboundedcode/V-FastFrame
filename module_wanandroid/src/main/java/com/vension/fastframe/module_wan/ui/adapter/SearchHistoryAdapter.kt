package com.vension.fastframe.module_wan.ui.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.fastframe.module_wan.R
import com.vension.fastframe.module_wan.bean.SearchHistoryBean

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/23 15:20
 * 描  述：热搜
 * ========================================================
 */

class SearchHistoryAdapter(private val context: Context?, datas: MutableList<SearchHistoryBean>)
    : BaseQuickAdapter<SearchHistoryBean, BaseViewHolder>(R.layout.item_search_history, datas) {

    override fun convert(helper: BaseViewHolder, item: SearchHistoryBean?) {
        helper ?: return
        item ?: return

        helper.setText(R.id.tv_search_key, item.key)
                .addOnClickListener(R.id.iv_clear)

    }
}