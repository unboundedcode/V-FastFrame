package com.vension.fastframe.view.ui.fragments

import android.os.Bundle
import android.view.View
import com.vension.fastframe.view.R
import com.vension.fastframe.view.widgets.progress.CircleProgressView
import com.vension.fastframe.view.widgets.progress.HorizontalProgressView
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_progress.*
import kv.vension.fastframe.core.AbsCompatFragment

/**
 * ========================================================================
 * @author: Created by Vension on 2019/9/2 16:56.
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
 * @desc: happy code -> 进度条
 * ========================================================================
 */
class ProgressFragment : AbsCompatFragment() ,HorizontalProgressView.HorizontalProgressUpdateListener, CircleProgressView.CircleProgressUpdateListener {


    override fun initToolBar(mCommonTitleBar: CommonTitleBar, title: String) {
        super.initToolBar(mCommonTitleBar, "精美进度条")
        mCommonTitleBar.apply {
            setBackgroundResource(R.drawable.view_shape_gra_bg_main)
        }
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_progress
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        btn_start.setOnClickListener(this)
        initProgressViews()
    }

    private fun initProgressViews() {
        hpv_language.setProgressViewUpdateListener(this)
        hpv_english.setProgressViewUpdateListener(this)
        hpv_history.setProgressViewUpdateListener(this)
        hpv_math.setProgressViewUpdateListener(this)
        progressView_circle_small.setProgressViewUpdateListener(this)

        progressView_circle_main.setGraduatedEnabled(true)
        progressView_circle_main.setProgressViewUpdateListener(this)
    }


    override fun onClick(v: View?) {
        super.onClick(v)
        when(v?.id){
            R.id.btn_start ->{
                //progressView_circle_small.startProgressAnimation();
                //progressView_circle_main.startProgressAnimation();
                hpv_language.startProgressAnimation()
                hpv_math.startProgressAnimation()
                hpv_history.startProgressAnimation()
                hpv_english.startProgressAnimation()
            }
        }
    }

    override fun onHorizontalProgressStart(view: View) {

    }

    override fun onHorizontalProgressUpdate(view: View, progress: Float) {
        val progressInt = progress.toInt()
        when (view.id) {
            R.id.hpv_language -> progress_text_language.text = "$progressInt%"

            R.id.hpv_english -> progress_text_english.text = "$progressInt%"

            R.id.hpv_history -> progress_text_history.text = "$progressInt%"

            R.id.hpv_math -> progress_text_math.text = "$progressInt%"
        }
    }

    override fun onHorizontalProgressFinished(view: View) {
        if (view.id == R.id.hpv_language) {
            progressView_circle_small.startProgressAnimation()
        }
    }

    override fun onCircleProgressStart(view: View) {

    }

    override fun onCircleProgressUpdate(view: View, progress: Float) {
        val progressInt = progress.toInt()
        if (view.id == R.id.progressView_circle_main) {
            progress_text_main.text = progressInt.toString() + ""
        }
    }

    override fun onCircleProgressFinished(view: View) {
        if (view.id == R.id.progressView_circle_small) {
            progressView_circle_main.startProgressAnimation()
        }

    }

    
    override fun lazyLoadData() {
    }

}