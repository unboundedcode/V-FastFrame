package kv.vension.fastframe.core.adapter.list

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

/**
 * ========================================================
 * @author: Created by Vension on 2018/9/12 14:08.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
class CommonListViewHolder {


    /**
     * layout文件中的控件集合
     * SparseArray用法与HashMap类似，性能比HashMap更优
     */
    private val mViews: SparseArray<View> = SparseArray()

    /**
     * BaseAdapter中的getView方法中对应的参数
     */
    private val mConvertView: View

    /**
     * 私有，禁止外部实例化
     * @param context
     * @param parent BaseAdapter中的getView方法中对应的参数
     * @param layoutId layout资源文件ID
     */
      constructor(context: Context, parent: ViewGroup?, layoutId: Int) {
        this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false)
        this.mConvertView.tag = this
    }

    /**
     *
     * @param context
     * @param convertView BaseAdapter中的getView方法中对应的参数
     * @param parent BaseAdapter中的getView方法中对应的参数
     * @param layoutId layout资源文件ID
     */

     fun get(context: Context, convertView: View?, parent: ViewGroup, layoutId: Int): CommonListViewHolder {
        return if (convertView == null) {
            CommonListViewHolder(context, parent, layoutId)
        } else convertView.tag as CommonListViewHolder
    }

    /**
     * 根据ViewID获取控件对象，先从mViews集合中查找，
     * 如果存在则直接返回该对象；
     * 不存在则从布局文件中获取该对象，然后添加到mViews集合中，然后再返回该对象；
     * @param viewid 控件ID
     * @param <T>
    </T> */
    fun <T : View> getView(viewid: Int): T {
        var view: View? = mViews.get(viewid)
        if (view == null) {
            view = mConvertView.findViewById(viewid)
            mViews.put(viewid, view)
        }
        return view as T
    }

    /**
     * 返回BaseAdapter中的getView方法中对应的参数（convertView）
     */
    fun getConvertView(): View {
        return mConvertView
    }

    /**
     * 获取TextView控件
     * @param viewid 控件ID
     */
    fun getTextView(viewid: Int): TextView {
        return getView<View>(viewid) as TextView
    }


    fun getCheckBox(viewid: Int): CheckBox {
        return getView<View>(viewid) as CheckBox
    }

    /**
     * 获取Button控件
     * @param viewid 控件ID
     */
    fun getButton(viewid: Int): Button {
        return getView<View>(viewid) as Button
    }

    /**
     * 获取ImageView控件
     * @param viewid 控件ID
     */
    fun getImageView(viewid: Int): ImageView {
        return getView<View>(viewid) as ImageView
    }

    /**
     * 获取ImageButton控件
     * @param viewid 控件ID
     */
    fun getImageButton(viewid: Int): ImageButton {
        return getView<View>(viewid) as ImageButton
    }

    fun getRatingBar(viewid: Int): RatingBar {
        return getView<View>(viewid) as RatingBar
    }

    /**
     * 设置TextView内容
     * @param viewid TextView控件ID
     * @param content 要设置的内容
     */
    fun setText(viewid: Int, content: String): CommonListViewHolder {
        getTextView(viewid).text = content
        return this
    }

    fun setVisibility(viewid: Int, visibility: Int): CommonListViewHolder {
        getView<View>(viewid).visibility = visibility
        return this
    }

    /**设置RatingBar */
    fun setRating(viewid: Int, rating: Float) {
        getRatingBar(viewid).rating = rating
    }


    fun setCheck(viewid: Int, isChecked: Boolean): CommonListViewHolder {
        getCheckBox(viewid).isChecked = isChecked
        return this
    }

    /**
     * 为ImageView设置图片
     * @param viewid ImageView控件ID
     * @param resid 要设置的图片资源ID
     */
    fun setImageResource(viewid: Int, resid: Int): CommonListViewHolder {
        getImageView(viewid).setImageResource(resid)
        return this
    }


}