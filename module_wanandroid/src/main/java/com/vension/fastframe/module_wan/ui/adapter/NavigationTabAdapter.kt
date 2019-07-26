package com.vension.fastframe.module_wan.ui.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import com.vension.fastframe.module_wan.R
import com.vension.fastframe.module_wan.bean.NavigationBean
import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/22 17:09
 * 描  述：
 * ========================================================
 */

class NavigationTabAdapter(context: Context?, list: List<NavigationBean>) : TabAdapter {

    private var context: Context = context!!
    private var list = mutableListOf<NavigationBean>()

    init {
        this.list = list as MutableList<NavigationBean>
    }

    override fun getIcon(position: Int): ITabView.TabIcon? = null


    override fun getBadge(position: Int): ITabView.TabBadge? = null

    override fun getBackground(position: Int): Int = -1

    override fun getTitle(position: Int): ITabView.TabTitle {
        return ITabView.TabTitle.Builder()
                .setContent(list[position].name)
                .setTextColor(
                    ContextCompat.getColor(context, R.color.colorAccent),
                        ContextCompat.getColor(context, R.color.color_black_alpha_50))
                .build()
    }

    override fun getCount(): Int = list.size

}