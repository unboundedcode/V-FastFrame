package com.vension.fastframe.module_wan.ui.adapter

import android.content.Context
import android.text.Html
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.fastframe.module_wan.R
import com.vension.fastframe.module_wan.bean.CollectionArticle
import kv.vension.vframe.glide.ImageLoader

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/23 11:13
 * 描  述：
 * ========================================================
 */

class CollectAdapter(private val context: Context?)
    : BaseQuickAdapter<CollectionArticle, BaseViewHolder>(R.layout.item_recy_collect) {
    override fun convert(helper: BaseViewHolder?, item: CollectionArticle?) {

        helper ?: return
        item ?: return

        if (item.envelopePic.isNotEmpty()) {
            helper.getView<ImageView>(R.id.iv_article_thumbnail).visibility = View.VISIBLE
            context?.let {
                ImageLoader.loadImage(it,helper.getView(R.id.iv_article_thumbnail),item.envelopePic)
            }
        } else {
            helper.getView<ImageView>(R.id.iv_article_thumbnail).visibility = View.GONE
        }
        helper.setText(R.id.tv_article_title, Html.fromHtml(item.title))
            .setText(R.id.tv_article_author, "作者：" + item.author)
            .setText(R.id.tv_article_date, "收藏时间：" + item.niceDate)
            .setImageResource(R.id.iv_like, R.drawable.ic_like_orange)
            .addOnClickListener(R.id.iv_like)

//        val chapterName = when {
//            item.superChapterName.isNotEmpty() and item.chapterName.isNotEmpty() ->
//                "${item.superChapterName} / ${item.chapterName}"
//            item.superChapterName.isNotEmpty() -> item.superChapterName
//            item.chapterName.isNotEmpty() -> item.chapterName
//            else -> ""
//        }
//        helper.setText(R.id.tv_article_chapterName, "类别：$chapterName")

        if (item.chapterName.isNotEmpty()) {
            helper.setText(R.id.tv_type_chapterName, item.chapterName)
        }
    }
}