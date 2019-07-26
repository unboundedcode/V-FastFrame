package com.vension.mvpforkotlin.sample.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vension.fastframe.module_wan.R


/**
 * ========================================================
 * @author: Created by Vension on 2018/11/7 18:08.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private var mData: List<String>? = null
    //使用接口回调点击事件
    private var mItemClickListener: OnItemClickListener? = null

    fun setData(data: List<String>) {
        mData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_lv_pop, null))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        viewHolder.mTextView.text = mData!![position]
        //条目点击事件
        mItemClickListener?.let {
            holder.itemView.setOnClickListener { mItemClickListener!!.onItemClick(mData!![position], position) }
        }
    }

    override fun getItemCount(): Int {
        return if (mData == null) 0 else mData!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTextView: TextView = itemView.findViewById(R.id.text_content)
    }

    fun addItemClickListener(itemClickListener: OnItemClickListener) {
        this.mItemClickListener = itemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(obj: Any?, position: Int)
    }

}