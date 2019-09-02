package com.vension.fastframe.view.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vension.fastframe.view.R
import com.vension.fastframe.view.bean.WidgetBean
import org.jetbrains.anko.imageResource
import java.util.*


/**
 * ========================================================================
 * @author: Created by Vension on 2019/9/2 14:30.
 * @email : vensionHu@qq.com
 * @Github: https://github.com/Vension
 * __      ________ _   _  _____ _____ ____  _   _
 * \ \    / /  ____| \ | |/ ____|_   _/ __ \| \ | |
 *  \ \  / /| |__  |  \| | (___   | || |  | |  \| |
 *   \ \/ / |  __| | . ` |\___ \  | || |  | | . ` |
 *    \  /  | |____| |\  |____) |_| || |__| | |\  |
 *     \/   |______|_| \_|_____/|_____\____/|_| \_|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * @desc: happy code ->
 * ========================================================================
 */
class MainGridAdapter(private val context: Context, private val datas: ArrayList<WidgetBean>)
    : RecyclerView.Adapter<MainGridAdapter.Holder>() {

    private var itemClickListener:onItemClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(context).inflate(R.layout.view_item_rv_main, parent, false))
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = datas[position]
        item?.let {
            holder.ivBg.imageResource = it.imgBg
            holder.tvName.text = it.name
            holder.tvDesc.text = it.desc
        }
        holder.itemView.setOnClickListener {
            if(itemClickListener != null){
                itemClickListener?.onItemClick(position)
            }
        }
    }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val ivBg: ImageView = itemView.findViewById(R.id.iv_widgetBg)
        internal val tvName: TextView = itemView.findViewById(R.id.tv_widget_name)
        internal val tvDesc: TextView = itemView.findViewById(R.id.tv_widget_desc)
    }

    fun addOnItemClickListener(listener:onItemClickListener) {
        this.itemClickListener = listener
    }

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

}