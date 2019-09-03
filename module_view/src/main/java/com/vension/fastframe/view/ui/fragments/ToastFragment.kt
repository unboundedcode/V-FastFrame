package com.vension.fastframe.view.ui.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.vension.fastframe.view.R
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_toast.*
import kv.vension.fastframe.core.AbsCompatFragment
import kv.vension.fastframe.utils.ToastHelper

/**
 * ========================================================================
 * @author: Created by Vension on 2019/9/3 11:09.
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
class ToastFragment : AbsCompatFragment() {

    override fun initToolBar(mCommonTitleBar: CommonTitleBar, title: String) {
        super.initToolBar(mCommonTitleBar, "可定制化Toast")
        mCommonTitleBar.apply {
            setBackgroundResource(R.drawable.view_shape_gra_bg_main)
        }
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_toast
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        btn_toastNormal.setOnClickListener(this)
        btn_toastTop.setOnClickListener(this)
        btn_toastCenter.setOnClickListener(this)
        btn_toastBottom.setOnClickListener(this)
        btn_toastInfo.setOnClickListener(this)
        btn_toastSuccess.setOnClickListener(this)
        btn_toastWarning.setOnClickListener(this)
        btn_toastError.setOnClickListener(this)
        btn_toastVertical.setOnClickListener(this)
        btn_toastCustom.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_toastNormal -> {//普通用法
                ToastHelper.normal("普通用法").show()
            }
            R.id.btn_toastTop -> {//显示在上方
                ToastHelper.success(Gravity.TOP,"显示在上方").show()
            }
            R.id.btn_toastCenter -> {
                ToastHelper.success(Gravity.CENTER,"显示在中间").show()
            }
            R.id.btn_toastBottom -> {
                ToastHelper.success(Gravity.BOTTOM,"显示在底部").show()
            }
            R.id.btn_toastInfo -> {
                ToastHelper.info("btn_toastInfo").show()
            }
            R.id.btn_toastSuccess -> {
                ToastHelper.success("btn_toastSuccess").show()
            }
            R.id.btn_toastWarning -> {
                ToastHelper.warning("btn_toastWarning").show()
            }
            R.id.btn_toastError -> {
                ToastHelper.error("btn_toastError").show()
            }
            R.id.btn_toastVertical -> {//竖向的
                ToastHelper.success("我是竖向的Toast", true).show()
            }
            R.id.btn_toastCustom -> {//自定义
                ToastHelper.makeCustom()
                    .setTypeFace(Typeface.DEFAULT_BOLD)
                    .setTextSize(16f)
                    .setCardRadius(25f)
                    .setCardElevation(10f)
                    .setIcon(R.drawable.ic_error_black_24dp)
                    .setCardBackgroundColor(R.color.colorAccent)
                    .setMessage("Fully customizable toast. But in a different way.")
                    .setGravity(Gravity.CENTER, 5, 5)
                    .createToast(Toast.LENGTH_LONG,true)
                    .show()
            }
        }
    }


    override fun lazyLoadData() {
    }

}