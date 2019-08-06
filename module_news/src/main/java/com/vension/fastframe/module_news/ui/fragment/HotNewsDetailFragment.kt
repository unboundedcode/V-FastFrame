package com.vension.fastframe.module_news.ui.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.vension.fastframe.module_news.R
import com.vension.fastframe.module_news.bean.NewsDetailModel
import com.vension.fastframe.module_news.mvp.contract.NewsDetailContract
import com.vension.fastframe.module_news.mvp.presenter.NewsDetailPresenter
import com.vension.fastframe.module_news.utils.DateTimeUtils
import com.vension.fastframe.module_news.utils.FileUtil
import com.vension.fastframe.module_news.utils.ToastUitl
import com.vension.fastframe.module_news.widget.texthtml.HtmlImageLoader
import com.vension.fastframe.module_news.widget.texthtml.HtmlText
import com.vension.fastframe.module_news.widget.texthtml.OnTagClickListener
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_hot_news_detail.*
import kv.vension.fastframe.VFrame
import kv.vension.fastframe.core.mvp.AbsCompatMVPFragment
import java.util.*

/**
 * ===================================================================
 * @author: Created by Vension on 2019/8/2 16:57.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/8/2 16:57
 * @desc:
 * ===================================================================
 */
class HotNewsDetailFragment : AbsCompatMVPFragment<NewsDetailContract.View, NewsDetailPresenter>(),NewsDetailContract.View {

    private var dataUrl = ""

    override fun initToolBar(mCommonTitleBar: CommonTitleBar, title: String) {
        super.initToolBar(mCommonTitleBar, "新闻详情")
        mCommonTitleBar.apply {
            setBackgroundColor(VFrame.getColor(R.color.colorNewsMain))
        }
    }

    override fun createPresenter(): NewsDetailPresenter {
        return NewsDetailPresenter()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_hot_news_detail
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        dataUrl = getBundleExtras().getString("articleUrl").toString()
    }

    override fun lazyLoadData() {
        if (!TextUtils.isEmpty(dataUrl)) {
            mPresenter.deatailItemRequest(dataUrl)
        } else {
            ToastUitl.showShort("详情地址为空")
        }
    }

    override fun detailItemData(data: NewsDetailModel) {
        if (data != null) {
            val content = data.content
            loadTextHtml(content)
            tvTitle.text = if (data.title == null) "" else data.title
            tvDatesSource.text = String.format(
                Locale.getDefault(),
                data.media.name + " | %s",
                DateTimeUtils.stampToTime(data.publish_time)
            )
        }
    }


    /**
     * 加载Html
     * @param content
     */
    internal var contentStr: String? = ""

    private fun loadTextHtml(content: String) {
        tvHtml.movementMethod = LinkMovementMethod.getInstance()
        if (TextUtils.isEmpty(content)) {
            contentStr = FileUtil.getSample(R.raw.hotnews_detail)
        } else {
            contentStr = content
        }
        HtmlText.from(contentStr)
            .setImageLoader(object : HtmlImageLoader {
                override fun getDefaultDrawable(): Drawable? {
                    return  VFrame.getDrawable(R.drawable.kv)
                }

                override fun getErrorDrawable(): Drawable? {
                    return  VFrame.getDrawable(R.drawable.place_image_default)
                }

                override fun getMaxWidth(): Int {
                    return getTextWidth()
                }

                override fun loadImage(url: String, callback: HtmlImageLoader.Callback) {
                    var url = url
                    // Glide sample, you can also use other image loader
                    if (!url.contains("http")) {
                        url = "http:$url"
                    }
                    var simpleTarget: SimpleTarget<Bitmap> = object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            callback.onLoadComplete(resource )
                        }
                    }

                    Glide.with(mContext)
                        .asBitmap()
                        .load(url)
                        .override(getTextWidth(), 600)
                        .into(simpleTarget)
                }

                override fun fitWidth(): Boolean {
                    return false
                }
            })
            .setOnTagClickListener(object : OnTagClickListener {
                override fun onImageClick(context: Context, imageUrlList: List<String>, position: Int) {
                    Toast.makeText(
                        context,
                        "image click, position: " + position + ", url: " + imageUrlList[position],
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onLinkClick(context: Context, url: String) {
                    Toast.makeText(context, "url click: $url", Toast.LENGTH_SHORT).show()
                    try {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            })
            .into(tvHtml)
    }


    private fun getTextWidth(): Int {
        val dm = resources.displayMetrics
        return dm.widthPixels - tvHtml.paddingLeft - tvHtml.paddingRight
    }

}