package kv.vension.fastframe.core.adapter.list

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import java.util.*

/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/8/9 15:59
 * 更 新：2019/8/9 15:59
 * 描 述：ListView、GridView通用的adapter
 * ========================================================
 */

abstract class CommonListAdapter<T> : BaseAdapter{

    private var mContext: Context//上下文
    private var listDatas = ArrayList<T>()//存放数据集合
    private var mLayoutId: Int = 0//item布局

    constructor(mContext: Context, mLayoutId: Int) {
        this.mContext = mContext
        this.mLayoutId = mLayoutId
    }

     constructor(mContext: Context, mLayoutId: Int, listDatas: ArrayList<T>) {
        this.mContext = mContext
         this.mLayoutId = mLayoutId
         this.listDatas = listDatas
    }

    override fun getCount(): Int {
        return listDatas.size
    }

    override fun getItem(position: Int): T {
        return listDatas[position]
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder:CommonListViewHolder
        if (convertView == null) {
            viewHolder = CommonListViewHolder(mContext, parent, mLayoutId)
        } else {
            viewHolder = convertView.tag as CommonListViewHolder
        }
        convert(viewHolder, position, listDatas[position])
        return viewHolder.getConvertView()
    }


    /**
     * 在子类中实现该方法
     * @param holder 列表项
     * @param item
     */
    abstract fun convert(holder: CommonListViewHolder, position: Int, item: T)


    /**
     * 添加单条数据项
     * @param item
     * @param addHead 是否添加在头部
     */
    fun addItem(addHead: Boolean, item: T) {
        if (addHead) {
            this.listDatas.add(0, item)
        } else {
            this.listDatas.add(item)
        }
        refresh()
    }

    /**
     * 移除单条数据项
     * @param position
     */
    fun removeItem(position: Int) {
        if (this.listDatas.size > 0) {
            this.listDatas.removeAt(position)
            refresh()
        }
    }

    /**
     * 添加多条数据项
     * @param lists
     * @param addHead 是否添加在头部
     */
    fun addData(addHead: Boolean, lists: List<T>) {
        this.listDatas.addAll(if (addHead) 0 else this.listDatas.size, lists)
        refresh()
    }

    /**
     * 设置数据源
     * @param data
     */
    fun setListDatas(data: List<T>) {
        listDatas.clear()
        listDatas.addAll(data)
        refresh()
    }

    /**
     * 清除数据源
     */
    fun clear() {
        this.listDatas.clear()
    }

    /**
     * 获取数据源
     */
    fun getDatas(): List<T> {
        return listDatas
    }

    /**
     * 刷新数据源
     */
    fun refresh() {
        this.notifyDataSetChanged()
    }


}

