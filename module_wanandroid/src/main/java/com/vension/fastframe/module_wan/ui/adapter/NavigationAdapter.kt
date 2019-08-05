package com.vension.fastframe.module_wan.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.fastframe.module_wan.R
import com.vension.fastframe.module_wan.bean.Article
import com.vension.fastframe.module_wan.bean.NavigationBean
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import kv.vension.vframe.ext.getRandomColor


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/22 16:52
 * 描  述：
 * ========================================================
 */

class NavigationAdapter(context: Context?, datas: MutableList<NavigationBean>)
    : BaseQuickAdapter<NavigationBean, BaseViewHolder>(R.layout.item_recy_navigation, datas) {

    private var mItemClickListener: OnItemClickListener? = null

    override fun convert(helper: BaseViewHolder, item: NavigationBean?) {
        item ?: return

        helper?.setText(R.id.item_navigation_tv, item.name)
        val flowLayout: TagFlowLayout? = helper?.getView(R.id.item_navigation_flow_layout)
        val articles: List<Article> = item.articles

        flowLayout?.run {
            adapter = object : TagAdapter<Article>(articles) {
                override fun getView(parent: FlowLayout?, position: Int, article: Article?): View? {

                    val tv: TextView = LayoutInflater.from(parent?.context).inflate(
                        R.layout.layout_flow_tv,
                        flowLayout, false) as TextView

                    article ?: return null

                    tv.text = article.title
                    tv.setTextColor(getRandomColor())

                    //条目点击事件
                    mItemClickListener?.let {
                        setOnTagClickListener { _, position, _ ->
                            var data: Article = articles[position]
                            it.onItemClick(data)
                            true
                        }
                    }
                    return tv
                }
            }
        }


    }


    fun addItemClickListener(itemClickListener: OnItemClickListener) {
        this.mItemClickListener = itemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(obj: Article)
    }


}