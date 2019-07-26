package kv.vension.vframe.core

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kv.vension.vframe.R

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/7 12:02
 * 描  述：xxDialogFragment dialog = xxDialogFragment()
 *        dialog.show(activity.supportFragmentManager, "tag")
 * ========================================================
 */

abstract class AbsCompatDialogFragment : DialogFragment(){

    abstract fun createView(): Int
    abstract fun initViews(parent: View)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //如果setCancelable()中参数为true，若点击dialog覆盖不到的activity的空白或者按返回键，则进行cancel，
        // 状态检测依次onCancel()和onDismiss()。如参数为false，则按空白处或返回键无反应。缺省为true
        isCancelable = false
        //可以设置dialog的显示风格，如style为STYLE_NO_TITLE，将被显示title。
        // 遗憾的是，我没有在DialogFragment中找到设置title内容的方法。theme为0，表示由系统选择合适的theme。
        val style = DialogFragment.STYLE_NORMAL
        val theme = 0
        setStyle(style, theme)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)//取消标题栏
        //设置dialog的 进出 动画
        dialog.window!!.setWindowAnimations(R.style.DialogTheme_Animation)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(createView(), null)
//        ButterKnife.bind(this, view)
        initViews(view)
        builder.setView(view)
        return builder.create()
    }

    //仅用于状态跟踪
    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
    }

    //仅用户状态跟踪
    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
    }

}