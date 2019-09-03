package com.vension.fastframe.view.ui

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.vension.fastframe.view.R
import com.vension.fastframe.view.bean.WidgetBean
import com.vension.fastframe.view.ui.adapter.MainGridAdapter
import com.vension.fastframe.view.ui.fragments.ProgressFragment
import com.vension.fastframe.view.ui.fragments.ToastFragment
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.view_activity_main_view.*
import kv.vension.fastframe.core.AbsCompatActivity
import kv.vension.fastframe.core.adapter.recy.decoration.GridItemDecoration
import lib.vension.fastframe.common.RouterConfig
import java.util.*



/**
 * ========================================================================
 * @author: Created by Vension on 2019/9/2 12:26.
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
@Route(path = RouterConfig.PATH_VIEW_MAINACTIVITY)
class ViewMainActivity : AbsCompatActivity() {

    override fun showToolBar(): Boolean {
        return true
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        super.initToolBar(mCommonTitleBar)
        mCommonTitleBar.apply {
            setBackgroundResource(R.drawable.view_shape_gra_bg_main)
            centerTextView.text = "收集优秀的开源控件/动效"
        }
    }

    override fun attachLayoutRes(): Int {
        return R.layout.view_activity_main_view
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        rv_thirdWidget.layoutManager = GridLayoutManager(this@ViewMainActivity,3)
        rv_thirdWidget.setHasFixedSize(true)
        val divider = GridItemDecoration.Builder(this@ViewMainActivity)
            .setHorizontalSpan(R.dimen.qb_px_1)
            .setVerticalSpan(R.dimen.qb_px_1)
            .setColorResource(R.color.colorAppLine)
            .setShowLastLine(true)
            .build()
        rv_thirdWidget.addItemDecoration(divider)
        val adapter = MainGridAdapter(this,getDatas())
        rv_thirdWidget.adapter = adapter
        adapter.addOnItemClickListener(listener)
    }

    private fun getDatas(): ArrayList<WidgetBean> {
        val list = ArrayList<WidgetBean>()
        list.add(WidgetBean(R.drawable.img_bg_star,"八大行星绕太阳3D旋转效果","https://github.com/GarrettLance/Demos"))
        list.add(WidgetBean(R.drawable.img_bg_progress,"精美、优雅的加载进度控件","https://github.com/Moosphan/Material-ProgressView"))
        list.add(WidgetBean(R.drawable.img_bg_toast,"可以自由定制的Toast","https://github.com/MrNtlu/Toastie"))
        return list
    }


    private val listener = object :MainGridAdapter.onItemClickListener{
        override fun onItemClick(position: Int) {
            when(position){
                0 -> {//八大行星绕太阳3D旋转效果
                    startActivity(Intent(this@ViewMainActivity, StarLandActivity::class.java).apply{
                        putExtra("isDemo",false)
                    })
                }
                1 -> {//精美、优雅的加载进度控件
                    startProxyActivity(ProgressFragment::class.java)
                }
                2 -> {//可以自由定制的Toast
                    startProxyActivity(ToastFragment::class.java)
                }
            }
        }
    }

}

