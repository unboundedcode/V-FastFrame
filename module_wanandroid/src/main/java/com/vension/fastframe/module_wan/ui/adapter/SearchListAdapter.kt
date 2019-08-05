package com.vension.mvpforkotlin.sample.ui.adapter

import android.content.Context
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.fastframe.module_wan.R
import com.vension.fastframe.module_wan.bean.Article
import kv.vension.vframe.glide.ImageLoader

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/6 15:23
 * 描  述：首页适配器
 * ========================================================
 */

class SearchListAdapter(private val context: Context?)
    : BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_recy_wan_home) {

    override fun convert(helper: BaseViewHolder, item: Article?) { item ?: return
        helper ?: return

        if (item.envelopePic.isNotEmpty()) {
            helper.getView<ImageView>(R.id.iv_article_thumbnail).visibility = View.VISIBLE
            context?.let {
                ImageLoader.loadImage(it,helper.getView(R.id.iv_article_thumbnail),item.envelopePic)
            }
        } else {
            helper.getView<ImageView>(R.id.iv_article_thumbnail).visibility = View.GONE
        }

        helper.setText(R.id.tv_article_title, Html.fromHtml(item.title))
            .setText(R.id.tv_article_author, "作者：${item.author}")
            .setText(R.id.tv_article_date, "时间：${item.niceDate}")
            .setImageResource(R.id.iv_like,
                if (item.collect) R.drawable.ic_like_orange else R.drawable.ic_like_gray
            )
            .addOnClickListener(R.id.iv_like)

//        val chapterName = when {
//            item.superChapterName.isNotEmpty() and item.chapterName.isNotEmpty() ->
//                "${item.superChapterName} / ${item.chapterName}"
//            item.superChapterName.isNotEmpty() -> item.superChapterName
//            item.chapterName.isNotEmpty() -> item.chapterName
//            else -> ""
//        }
//        helper.setText(R.id.tv_article_chapterName, "分类：$chapterName")
        if (item.chapterName.isNotEmpty()) {
            helper.setText(R.id.tv_type_chapterName, item.chapterName)
        }
        if (item.superChapterName.isNotEmpty()) {
            helper.setText(R.id.tv_type_superChapterName, item.superChapterName)
        }

        helper.getView<TextView>(R.id.tv_article_fresh).visibility = if(item.fresh) View.VISIBLE else View.GONE
        helper.getView<TextView>(R.id.tv_article_top).visibility = if(item.top == "1") View.VISIBLE else View.GONE
        helper.getView<TextView>(R.id.tv_article_tag).text = if(item.tags.size > 0) item.tags[0].name else ""
        helper.getView<TextView>(R.id.tv_article_tag).visibility = if(item.tags.size > 0) View.VISIBLE else View.GONE

    }

}
