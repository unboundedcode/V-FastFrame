package kv.vension.fastframe.core.adapter.recy

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/3/30 16:47
 * 描  述：多布局条目类型
 * ========================================================
 */

interface MultipleType<in T> {
    fun getLayoutId(item: T, position: Int): Int
}
