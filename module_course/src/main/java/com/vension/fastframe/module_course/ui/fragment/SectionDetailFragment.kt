package com.vension.fastframe.module_course.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.*
import com.vension.fastframe.module_course.R
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_section_detail.*
import kv.vension.fastframe.VFrame
import kv.vension.fastframe.core.AbsCompatFragment

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/26 14:58.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/26 14:58
 * @desc:
 * ===================================================================
 */
class SectionDetailFragment : AbsCompatFragment() {

    private var mUrl: String? = "https://ke.youdao.com/course/detail/18906?inLoc=fp_h_0&Pdt=CourseWeb"

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_section_detail
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar,title:String) {
        super.initToolBar(mCommonTitleBar,"微课")
        mCommonTitleBar.run {
            setBackgroundColor(VFrame.getColor(R.color.colorCourseMain))
        }
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        val webChromeClient = WebClient()
        val webViewClient = WebClientBase()
        val webSettings = web_view.settings
        //js支持
        webSettings.javaScriptEnabled = true
        //js脚本
        webSettings.javaScriptCanOpenWindowsAutomatically = false
        //设置缓存
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        webSettings.domStorageEnabled = true
        webSettings.setGeolocationEnabled(true)
        webSettings.useWideViewPort = true //关键点
        webSettings.builtInZoomControls = true //设置缩放
        webSettings.loadWithOverviewMode = true //全屏
        webSettings.setSupportZoom(true)
        webSettings.displayZoomControls = false
        webSettings.setAppCacheEnabled(true)
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        web_view.isDrawingCacheEnabled = true
        web_view.settings.blockNetworkImage = true
        web_view.webViewClient = webViewClient
        web_view.requestFocus(View.FOCUS_DOWN)
        web_view.settings.defaultTextEncodingName = "UTF-8"
        web_view.webChromeClient = webChromeClient
        web_view.loadUrl(mUrl)
    }


    internal inner class WebClientBase : WebViewClient() {

        override fun onPageFinished(webView: WebView, s: String) {
            super.onPageFinished(webView, s)
            mLoadingDialog.dismiss()
            web_view.settings.blockNetworkImage = false
            val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            web_view.measure(w, h)
            web_view.loadUrl("javascript:function setTop(){document.querySelector('#bannercontainer').style.display=\"none\";}setTop();");
        }

        override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
            super.onReceivedHttpError(view, request, errorResponse)
            val errorHtml = "<html><body><h2>找不到网页</h2></body></html>"
            web_view.loadDataWithBaseURL(null, errorHtml, "text/html", "UTF-8", null)
        }

    }

    internal inner class WebClient : WebChromeClient() {

        override fun onProgressChanged(webView: WebView, i: Int) {
            web_view.settings.blockNetworkImage = false
            web_view.loadUrl("javascript:function setTop(){document.querySelector('#bannercontainer').style.display=\"none\";}setTop();");
            super.onProgressChanged(webView, i)
        }
    }

    override fun lazyLoadData() {
        mLoadingDialog.show()
    }
}