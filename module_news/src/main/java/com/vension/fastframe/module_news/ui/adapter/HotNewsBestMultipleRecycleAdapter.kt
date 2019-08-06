package com.vension.fastframe.module_news.ui.adapter

import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.fastframe.module_news.NewsConstant
import com.vension.fastframe.module_news.bean.HotNewsBestModel
import com.vension.fastframe.module_news.utils.GlideImageLoader
import com.vension.fastframe.module_news.utils.ToastUitl
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.transformer.*
import kv.vension.fastframe.views.ShapeImageView
import java.util.*


/**
 * ===================================================================
 * @author: Created by Vension on 2019/8/5 10:53.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/8/5 10:53
 * @desc:热闻精选数据装配
 * ===================================================================
 */
class HotNewsBestMultipleRecycleAdapter( data: ArrayList<HotNewsBestModel.Articles>) :
    BaseMultiItemQuickAdapter<HotNewsBestModel.Articles, BaseViewHolder>(data) , OnBannerListener {

    private val mImageLoader = GlideImageLoader()

    init {
        addItemType(HotNewsBestModel.Articles.TYPE_HORIZAONTAL_IMAGE, com.vension.fastframe.module_news.R.layout.item_index_horizontal_list)
        addItemType(HotNewsBestModel.Articles.TYPE_EMPTY_IMAGE, com.vension.fastframe.module_news.R.layout.item_index_zeroempty_image)
        addItemType(HotNewsBestModel.Articles.TYPE_TYADMODE_IMAGE, com.vension.fastframe.module_news.R.layout.item_index_tyad_menu)
        addItemType(HotNewsBestModel.Articles.TYPE_TOP_BANNER, com.vension.fastframe.module_news.R.layout.item_index_banner_with_menu)
        addItemType(HotNewsBestModel.Articles.TYPE_SINGLE_IMAGE, com.vension.fastframe.module_news.R.layout.item_index_single_image)
        addItemType(HotNewsBestModel.Articles.TYPE_THREE_IMAGE, com.vension.fastframe.module_news.R.layout.item_index_three_image)
        addItemType(HotNewsBestModel.Articles.TYPE_SINGLE_BIG_IMAGE, com.vension.fastframe.module_news.R.layout.item_index_single_big_image)
    }

    override fun convert(helper: BaseViewHolder, item: HotNewsBestModel.Articles?) {
        val adapterPosition = helper.adapterPosition
        when (item?.itemType) {
            0//banner
            -> bindTopBannerData(helper, item, adapterPosition)
            1//single
            -> bindSingleNewsData(helper, item, adapterPosition)
            2//ad-通用
            -> bindTYAdImageData(helper, item, adapterPosition)
            3//multi
            -> {
                bindMultiImgNewsData(helper, item, adapterPosition)
            }
            4//Empty
            -> {
                bindEmptyImgNewsData(helper, item, adapterPosition)
            }
            5//Horizaontal
            -> {
//                bindHorizaontalImgNewsData(helper, item, adapterPosition)
            }
            10//single BIG-IMAGE
            -> bindSingleBigNewsData(helper, item, adapterPosition)
            else -> {
            }
        }
    }

    /**
     * 轮播图
     *
     * @param position
     */
    override fun OnBannerClick(position: Int) {
        ToastUitl.showShort("轮播图，点击了：$position")
    }

    /**
     * 绑定banner数据
     *
     * @param helper
     * @param item
     * @param position
     */
    private var bannerList: MutableList<String>? = null

    private fun bindTopBannerData(helper: BaseViewHolder, item: HotNewsBestModel.Articles, position: Int) {
        val mBanner = helper.getView<Banner>(com.vension.fastframe.module_news.R.id.banner1)
        //        mBanner.setBannerStyle()
        val titles = ArrayList<String>()
        if (titles != null && titles.size > 0) titles.clear()
        if (bannerList != null)
            bannerList!!.clear()
        else
            bannerList = ArrayList()
        initAnim()
        //数据
        for (a in item.pics) {
            bannerList!!.add(a.url)
            titles.add("")
        }
        //默认是NUM_INDICATOR_TITLE
        mBanner.setImages(bannerList!!)
            //.setBannerTitles(titles)
            .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            .setIndicatorGravity(BannerConfig.CENTER)
            .setImageLoader(GlideImageLoader())
            .setOnBannerListener(this)
            .setIndicatorGravity(Gravity.CENTER_HORIZONTAL)
            //设置指示器位置（当banner模式中有指示器时）
            .setIndicatorGravity(BannerConfig.CENTER)
            .isAutoPlay(true)
            .setBannerAnimation(transformers[1])
            .setDelayTime(3000)
            .start()

        mImageLoader.displayImage(mContext, NewsConstant.HOME_MENU_URL_A, helper.getView<ImageView>(com.vension.fastframe.module_news.R.id.menu_a))
        mImageLoader.displayImage(mContext, NewsConstant.HOME_MENU_URL_B, helper.getView<ImageView>(com.vension.fastframe.module_news.R.id.menu_b))
        mImageLoader.displayImage(mContext, NewsConstant.HOME_MENU_URL_C, helper.getView<ImageView>(com.vension.fastframe.module_news.R.id.menu_c))
        mImageLoader.displayImage(mContext, NewsConstant.HOME_MENU_URL_D, helper.getView<ImageView>(com.vension.fastframe.module_news.R.id.menu_d))
        mImageLoader.displayImage(mContext, NewsConstant.HOME_MENU_URL_E, helper.getView<ImageView>(com.vension.fastframe.module_news.R.id.menu_e))
    }


    /**
     * 单图
     *
     * @param helper
     * @param item
     * @param position
     */
    private fun bindSingleNewsData(helper: BaseViewHolder, item: HotNewsBestModel.Articles, position: Int) {
        mImageLoader.displayImage(mContext, item.pics[0].url, helper.getView<ImageView>(com.vension.fastframe.module_news.R.id.article_img))
        helper.setText(com.vension.fastframe.module_news.R.id.tvSourceName, if (item.mediaName == null) "" else item.mediaName)
        helper.setText(com.vension.fastframe.module_news.R.id.tvCommentnum, item.commentNum.toString() + "")
        helper.setText(com.vension.fastframe.module_news.R.id.tvArticletitle, if (item.title == null) "" else item.title)

        if (item.flag != null) {
            if (1 == item.flag.type || 3 == item.flag.type) {//1：热点  3：精选
                helper.getView<View>(com.vension.fastframe.module_news.R.id.hotFlag).visibility = View.VISIBLE
                helper.getView<View>(com.vension.fastframe.module_news.R.id.llZhuanTi).visibility = View.GONE
                (helper.getView<View>(com.vension.fastframe.module_news.R.id.hotFlag) as TextView).text =
                    if (item.flag.text == null) "" else item.flag.text
            } else if (4 == item.flag.type) {//专题
                helper.getView<View>(com.vension.fastframe.module_news.R.id.hotFlag).visibility = View.GONE
                helper.getView<View>(com.vension.fastframe.module_news.R.id.llZhuanTi).setVisibility(View.VISIBLE)
            } else {
                helper.getView<View>(com.vension.fastframe.module_news.R.id.hotFlag).setVisibility(View.GONE)
                helper.getView<View>(com.vension.fastframe.module_news.R.id.llZhuanTi).setVisibility(View.GONE)
            }
        }

    }


    /**
     * 单张大图
     * TODO:----------这里对绑定控件及数据都做了空判断处理-------------------------
     * @param helper
     * @param item
     * @param adapterPosition
     */
    private fun bindSingleBigNewsData(helper: BaseViewHolder, item: HotNewsBestModel.Articles, adapterPosition: Int) {
        val tvArticletitle_best = helper.getView<TextView>(com.vension.fastframe.module_news.R.id.tvArticletitle_best)
        val tvSourceName_best = helper.getView<TextView>(com.vension.fastframe.module_news.R.id.tvSourceName_best)
        val tvCommentnum_best = helper.getView<TextView>(com.vension.fastframe.module_news.R.id.tvCommentnum_best)
        val img_single_big = helper.getView<View>(com.vension.fastframe.module_news.R.id.img_single_big) as ShapeImageView
        if (tvArticletitle_best != null) tvArticletitle_best.text = if (item.title == null) "" else item.title
        if (tvSourceName_best != null) tvSourceName_best.text = if (item.mediaName == null) "" else item.mediaName
        if (tvCommentnum_best != null) tvCommentnum_best.text = item.commentNum.toString() + ""
        if(img_single_big != null){
            mImageLoader.displayImage(mContext, item.pics[0].url, img_single_big)
        }
    }


    /**
     * 通用广告-三图拼接
     *
     * @param helper
     * @param item
     * @param adapterPosition
     */
    private fun bindTYAdImageData(helper: BaseViewHolder, item: HotNewsBestModel.Articles, adapterPosition: Int) {
        if (item.pics.size >= 3) {
            mImageLoader.displayImage(mContext, item.pics[0].url, helper.getView<ImageView>(com.vension.fastframe.module_news.R.id.tymenu_a))
            mImageLoader.displayImage(mContext, item.pics[1].url, helper.getView<ImageView>(com.vension.fastframe.module_news.R.id.tymenu_b))
            mImageLoader.displayImage(mContext, item.pics[2].url, helper.getView<ImageView>(com.vension.fastframe.module_news.R.id.tymenu_c))
        }
    }


    /**
     * 三图
     * @param helper
     * @param item
     * @param position
     */
    private fun bindMultiImgNewsData(helper: BaseViewHolder, item: HotNewsBestModel.Articles, position: Int) {
        if (item.pics.size >= 3) {
            mImageLoader.displayImage(mContext, item.pics[0].url, helper.getView<ImageView>(com.vension.fastframe.module_news.R.id.article_img1))
            mImageLoader.displayImage(mContext, item.pics[1].url, helper.getView<ImageView>(com.vension.fastframe.module_news.R.id.article_img2))
            mImageLoader.displayImage(mContext, item.pics[2].url, helper.getView<ImageView>(com.vension.fastframe.module_news.R.id.article_img3))
        }
        helper.setText(com.vension.fastframe.module_news.R.id.Tarticle_title, if (item.title == null) "" else item.title)
        helper.setText(com.vension.fastframe.module_news.R.id.tvTSourceName, if (item.mediaName == null) "" else item.mediaName)
        helper.setText(com.vension.fastframe.module_news.R.id.tvTcommetnum, item.commentNum.toString() + "")
        helper.setText(com.vension.fastframe.module_news.R.id.tvDate, item.virtualTime.toString() + "")

        if (item.flag != null) {
            if (1 == item.flag.type || 3 == item.flag.type) {//1：热点  3：精选
                helper.getView<View>(com.vension.fastframe.module_news.R.id.hotFlag).visibility = View.VISIBLE
                helper.getView<View>(com.vension.fastframe.module_news.R.id.llZhuanTi).visibility = View.GONE
                (helper.getView<View>(com.vension.fastframe.module_news.R.id.hotFlag) as TextView).text =
                    if (item.flag.text == null) "" else item.flag.text
            } else if (4 == item.flag.type) {//专题
                helper.getView<View>(com.vension.fastframe.module_news.R.id.hotFlag).visibility = View.GONE
                helper.getView<View>(com.vension.fastframe.module_news.R.id.llZhuanTi).visibility = View.VISIBLE
            } else {
                helper.getView<View>(com.vension.fastframe.module_news.R.id.hotFlag).visibility = View.GONE
                helper.getView<View>(com.vension.fastframe.module_news.R.id.llZhuanTi).visibility = View.GONE
            }
        }
    }

    /**
     * 无图
     *
     * @param helper
     * @param item
     * @param position
     */
    private fun bindEmptyImgNewsData(helper: BaseViewHolder, item: HotNewsBestModel.Articles, position: Int) {
        helper.setText(com.vension.fastframe.module_news.R.id.Tarticle_title, if (item.title == null) "" else item.title)
        helper.setText(com.vension.fastframe.module_news.R.id.tvTSourceName, if (item.mediaName == null) "" else item.mediaName)
        helper.setText(com.vension.fastframe.module_news.R.id.tvTcommetnum, item.commentNum.toString() + "")
        helper.setText(com.vension.fastframe.module_news.R.id.tvDate, item.virtualTime.toString() + "")
    }



    /**
     * banner动画
     */
    private val transformers = ArrayList<Class<out ViewPager.PageTransformer>>()
    private fun initAnim() {
        transformers.add(DefaultTransformer::class.java)
        transformers.add(AccordionTransformer::class.java)
        transformers.add(BackgroundToForegroundTransformer::class.java)
        transformers.add(ForegroundToBackgroundTransformer::class.java)
        transformers.add(CubeInTransformer::class.java)//兼容问题，慎用
        transformers.add(CubeOutTransformer::class.java)
        transformers.add(DepthPageTransformer::class.java)
        transformers.add(FlipHorizontalTransformer::class.java)//7
        transformers.add(FlipVerticalTransformer::class.java)
        transformers.add(RotateDownTransformer::class.java)
        transformers.add(RotateUpTransformer::class.java)
        transformers.add(ScaleInOutTransformer::class.java)
        transformers.add(StackTransformer::class.java)
        transformers.add(TabletTransformer::class.java)
        transformers.add(ZoomInTransformer::class.java)
        transformers.add(ZoomOutTranformer::class.java)
        transformers.add(ZoomOutSlideTransformer::class.java)
    }


}