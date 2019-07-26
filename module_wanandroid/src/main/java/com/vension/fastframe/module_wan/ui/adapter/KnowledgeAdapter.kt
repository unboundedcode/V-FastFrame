package com.vension.fastframe.module_wan.ui.adapter

import android.content.Context
import android.text.Html
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.fastframe.module_wan.R
import com.vension.fastframe.module_wan.bean.Article
import kv.vension.vframe.glide.ImageLoader

class KnowledgeAdapter(private val context: Context?) : BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_recy_knowledge) {

    override fun convert(helper: BaseViewHolder?, item: Article?) {
        item ?: return
        helper ?: return

        if (item.envelopePic.isNotEmpty()) {
            helper.getView<ImageView>(R.id.iv_article_thumbnail).visibility = View.VISIBLE
            context?.let {
                ImageLoader.loadImage(it,helper.getView(R.id.iv_article_thumbnail),item.envelopePic)
            }
        } else {
            helper.getView<ImageView>(R.id.iv_article_thumbnail).visibility = View.GONE
        }

        helper
            .setText(R.id.tv_article_title, Html.fromHtml(item.title))
            .setText(R.id.tv_article_author, "作者：" + item.author)
            .setText(R.id.tv_article_date, "时间：" + item.niceDate)
            .setImageResource(R.id.iv_like, if (item.collect) R.drawable.ic_like_orange else R.drawable.ic_like_gray)
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
        if (item.superChapterName.isNotEmpty()) {
            helper.setText(R.id.tv_type_superChapterName, item.superChapterName)
        }

    }

}