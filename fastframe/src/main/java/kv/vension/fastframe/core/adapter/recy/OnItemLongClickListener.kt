package kv.vension.fastframe.core.adapter.recy

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/3/30 16:50
 * 描  述：Adapter条目的长按事件
 * ========================================================
 */

interface OnItemLongClickListener {

    fun onItemLongClick(obj: Any?, position: Int): Boolean

}
