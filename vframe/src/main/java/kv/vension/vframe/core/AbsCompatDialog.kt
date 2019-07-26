package kv.vension.vframe.core

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialog
import kv.vension.vframe.R

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/7 11:39.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
abstract class AbsCompatDialog : AppCompatDialog, View.OnClickListener {

    protected lateinit var mContext: Context
    private var tag: Any? = null

    protected abstract fun bindLayoutId(): Int //绑定dialog布局
    protected abstract fun init() //初始化views

    constructor(context: Context) : super(context, R.style.DialogTheme_Animation) {
        this.mContext = context
    }

    constructor(context: Context,themeId:Int) : super(context, themeId) {
        this.mContext = context
    }

    constructor(context: Context,cancelable:Boolean,cancelListener: DialogInterface.OnCancelListener) : super(context, cancelable,cancelListener) {
        this.mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAnimationStyle(R.style.DialogTheme_Animation)//设置弹窗动画
        setContentView(bindLayoutId())//
//        ButterKnife.bind(this)
        val fLayoutParams = window!!.attributes
        fLayoutParams.dimAmount = 0.3f
        fLayoutParams.gravity = Gravity.CENTER
        window!!.attributes = fLayoutParams
        window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        this.init()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            this.cancel()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun setAnimationStyle(styleId: Int) {
        this.window!!.setWindowAnimations(styleId)
    }

    fun getTag(): Any {
        return tag!!
    }

    fun setTag(obj: Any): AbsCompatDialog {
        this.tag = obj
        return this
    }

    override fun onClick(v: View?) {
        //TODO 点击事件
    }

}