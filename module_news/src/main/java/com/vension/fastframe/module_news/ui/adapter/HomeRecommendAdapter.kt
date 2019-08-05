package com.vension.fastframe.module_news.ui.adapter

import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import cn.jzvd.JZDataSource
import cn.jzvd.JzvdStd
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.fastframe.module_news.NewsConstant
import com.vension.fastframe.module_news.R
import com.vension.fastframe.module_news.bean.HomeTophotIndex
import com.vension.fastframe.module_news.bean.Menu9Model
import com.vension.fastframe.module_news.ui.fragment.HotNewsBestFragment
import com.vension.fastframe.module_news.ui.fragment.HotNewsDetailFragment
import com.vension.fastframe.module_news.utils.DateTimeUtils
import com.vension.fastframe.module_news.utils.GlideImageLoader
import com.vension.fastframe.module_news.utils.ToastUitl
import com.vension.fastframe.module_news.widget.PulToLeftViewGroup
import com.vension.fastframe.module_news.widget.UpDownViewSwitcher
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.transformer.*
import kv.vension.vframe.core.AbsCompatFragment
import kv.vension.vframe.glide.GlideApp
import kv.vension.vframe.views.ShapeImageView
import java.util.*
import kotlin.collections.LinkedHashMap
import kotlin.math.ceil

/**
 * ===================================================================
 * @author: Created by Vension on 2019/8/2 13:08.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/8/2 13:08
 * @desc:
 * ===================================================================
 */
class HomeRecommendAdapter(private val mFragment:AbsCompatFragment, data: ArrayList<HomeTophotIndex.Articles>) :
    BaseMultiItemQuickAdapter<HomeTophotIndex.Articles, BaseViewHolder>(data) , OnBannerListener {

    private var mPagerList: MutableList<View>? = null
    private var mDatas: MutableList<Menu9Model>? = null
    private var inflater: LayoutInflater? = null
    /**
     * 总的页数
     */
    private var pageCount: Int = 0
    /**
     * 每一页显示的个数
     */
    private val pageSize = 10
    /**
     * 当前显示的是第几页
     */
    private var curIndex = 0
    private val mImageLoader = GlideImageLoader()


    init {
        addItemType(HomeTophotIndex.Articles.TYPE_TOP_BANNER, R.layout.item_index_banner_with_menu)
        addItemType(HomeTophotIndex.Articles.TYPE_SINGLE_IMAGE, R.layout.item_index_single_image)
        addItemType(HomeTophotIndex.Articles.TYPE_TYADMODE_IMAGE, R.layout.item_index_tyad_menu)
        addItemType(HomeTophotIndex.Articles.TYPE_THREE_IMAGE, R.layout.item_index_three_image)
        addItemType(HomeTophotIndex.Articles.TYPE_EMPTY_IMAGE, R.layout.item_index_zeroempty_image)
        addItemType(HomeTophotIndex.Articles.TYPE_HORIZAONTAL_IMAGE, R.layout.item_index_horizontal_list)
        addItemType(HomeTophotIndex.Articles.TYPE_UP_VIDEO_IMAGE, R.layout.item_index_video_image)
        addItemType(HomeTophotIndex.Articles.TYPE_UP_DOWN_SWITCHER, R.layout.item_index_bullentin)
        addItemType(HomeTophotIndex.Articles.TYPE_HORIZAONTAL_NINE_MENU, R.layout.item_index_horizontal_9menu)
        addItemType(HomeTophotIndex.Articles.TYPE_SINGLE_BIG_IMAGE, R.layout.item_index_single_big_image)
        addItemType(HomeTophotIndex.Articles.TYPE_VIDEO_CANPLAY, R.layout.item_video_home)
    }

    override fun convert(helper: BaseViewHolder, articles: HomeTophotIndex.Articles) {
        val adapterPosition = helper.adapterPosition
        when (articles.itemType) {
            0//banner
            -> bindTopBannerData(helper, articles, adapterPosition)
            1//single
            -> bindSingleNewsData(helper, articles, adapterPosition)
            2//ad-通用
            -> bindTYAdImageData(helper, articles, adapterPosition)
            3//multi
            -> {
                bindMultiImgNewsData(helper, articles, adapterPosition)
                bindEmptyImgNewsData(helper, articles, adapterPosition)
                bindHorizaontalImgNewsData(helper, articles, adapterPosition)
            }
            4//Empty
            -> {
                bindEmptyImgNewsData(helper, articles, adapterPosition)
                bindHorizaontalImgNewsData(helper, articles, adapterPosition)
            }
            5//Horizaontal
            -> bindHorizaontalImgNewsData(helper, articles, adapterPosition)
            7//视频封面
            -> bindVideoImageData(helper, articles, adapterPosition)
            8//上下滚动广告条
            -> bindUpdownSwitcherData(helper, articles, adapterPosition)
            9//Horizaontal 9menu
            -> bindHorizaontal9MenuData(helper, articles, adapterPosition)
            10//single BIG-IMAGE
            -> {
                bindSingleBigNewsData(helper, articles, adapterPosition)
                bindVideoPlayerNewsData(helper, articles, adapterPosition)
            }
            HomeTophotIndex.Articles.TYPE_VIDEO_CANPLAY//videoplayer
            -> bindVideoPlayerNewsData(helper, articles, adapterPosition)
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

    private fun bindTopBannerData(helper: BaseViewHolder, item: HomeTophotIndex.Articles, position: Int) {
        val mBanner = helper.getView<Banner>(R.id.banner1)
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

        mImageLoader.displayImage(mContext, NewsConstant.HOME_MENU_URL_A, helper.getView<ImageView>(R.id.menu_a))
        mImageLoader.displayImage(mContext, NewsConstant.HOME_MENU_URL_B, helper.getView<ImageView>(R.id.menu_b))
        mImageLoader.displayImage(mContext, NewsConstant.HOME_MENU_URL_C, helper.getView<ImageView>(R.id.menu_c))
        mImageLoader.displayImage(mContext, NewsConstant.HOME_MENU_URL_D, helper.getView<ImageView>(R.id.menu_d))
        mImageLoader.displayImage(mContext, NewsConstant.HOME_MENU_URL_E, helper.getView<ImageView>(R.id.menu_e))
    }

    /**
     * 单图
     *
     * @param helper
     * @param item
     * @param position
     */
    private fun bindSingleNewsData(helper: BaseViewHolder, item: HomeTophotIndex.Articles, position: Int) {
        val view = helper.getView<LinearLayout>(R.id.llContent)
        mImageLoader.displayImage(mContext, item.pics[0].url, helper.getView<ImageView>(R.id.article_img))
        helper.setText(R.id.tvSourceName, if (item.mediaName == null) "" else item.mediaName)
        helper.setText(R.id.tvCommentnum, item.commentNum.toString() + "")
        helper.setText(R.id.tvArticletitle, if (item.title == null) "" else item.title)
        helper.setText(R.id.tvDate, DateTimeUtils.getLongDateToString(item.virtualTime) + "")
        view.setOnClickListener {
            satrtNewsDetail(item.articleUrl)
        }
    }


    /**
     * 通用广告-三图拼接
     *
     * @param helper
     * @param item
     * @param adapterPosition
     */
    private fun bindTYAdImageData(helper: BaseViewHolder, item: HomeTophotIndex.Articles, adapterPosition: Int) {
        if (item.pics.size >= 3) {
            mImageLoader.displayImage(mContext, item.pics[0].url, helper.getView<ImageView>(R.id.tymenu_a))
            mImageLoader.displayImage(mContext, item.pics[1].url, helper.getView<ImageView>(R.id.tymenu_b))
            mImageLoader.displayImage(mContext, item.pics[2].url, helper.getView<ImageView>(R.id.tymenu_c))
        }
    }


    /**
     * 三图
     *
     * @param helper
     * @param item
     * @param position
     */
    private fun bindMultiImgNewsData(helper: BaseViewHolder, item: HomeTophotIndex.Articles, position: Int) {
        val view = helper.getView<LinearLayout>(R.id.llContent)
        if (item.pics.size >= 3) {
            mImageLoader.displayImage(mContext, item.pics[0].url, helper.getView<ImageView>(R.id.article_img1))
            mImageLoader.displayImage(mContext, item.pics[1].url, helper.getView<ImageView>(R.id.article_img2))
            mImageLoader.displayImage(mContext, item.pics[2].url, helper.getView<ImageView>(R.id.article_img3))
        }
        helper.setText(R.id.Tarticle_title, if (item.title == null) "" else item.title)
        helper.setText(R.id.tvTSourceName, if (item.mediaName == null) "" else item.mediaName)
        helper.setText(R.id.tvTcommetnum, item.commentNum.toString() + "")
        helper.setText(R.id.tvDate, DateTimeUtils.getLongDateToString(item.virtualTime) + "")
        view.setOnClickListener {
            satrtNewsDetail(if(item.articleUrl == null) "" else item.articleUrl)
        }
    }


    /**
     * 无图
     *
     * @param helper
     * @param item
     * @param position
     */
    private fun bindEmptyImgNewsData(helper: BaseViewHolder, item: HomeTophotIndex.Articles, position: Int) {
        helper.setText(R.id.Tarticle_title, if (item.title == null) "" else item.title)
        helper.setText(R.id.tvTSourceName, if (item.mediaName == null) "" else item.mediaName)
        helper.setText(R.id.tvTcommetnum, item.commentNum.toString() + "")
        helper.setText(R.id.tvDate, DateTimeUtils.getLongDateToString(item.virtualTime) + "")
        val view = helper.getView<LinearLayout>(R.id.llSingleRoot)
        view.setOnClickListener {
            satrtNewsDetail(if(item.articleUrl == null) "" else item.articleUrl)
        }
    }



    /**
     * 横向图片新闻列表
     *
     * @param helper
     * @param item
     * @param adapterPosition
     */
    private fun bindHorizaontalImgNewsData(helper: BaseViewHolder, item: HomeTophotIndex.Articles, adapterPosition: Int) {
        val recyclerView = helper.getView<RecyclerView>(R.id.spike_content_view)
        val rlMore = helper.getView<RelativeLayout>(R.id.rlMore)
        val llHorizontalList = helper.getView<LinearLayout>(R.id.llHorizontalList)
        val mHorizontalPullGroup = helper.getView<PulToLeftViewGroup>(R.id.pull_group)
        //拖拽操作
        initDragMoreLayout(mHorizontalPullGroup)
        val hotSpecialNews = item.hotSpecialNews
        if (item.hotSpecialNews == null || item.hotSpecialNews.size <= 0) return
        (helper.getView<View>(R.id.hotNewsTitleIco) as ImageView).setImageResource(R.drawable.icon_title_hotnews)
        helper.setText(R.id.hotnewsMoreText, "查看更多")
        rlMore.setOnClickListener {
            satrtHotNewsBest()
        }
        //Recyclerview
        val linearLayoutManager = LinearLayoutManager(mContext)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.isFocusable = false
        recyclerView.isFocusableInTouchMode = false
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(10)
        val adapter = SpikeContentAdapter(mContext, R.layout.item_index_horizontal_list, hotSpecialNews)
        adapter.setEnableLoadMore(true)
        recyclerView.adapter = adapter
    }


    /**
     * 视频封面大图
     *
     * @param helper
     * @param item
     * @param adapterPosition
     */
    private fun bindVideoImageData(helper: BaseViewHolder, item: HomeTophotIndex.Articles, adapterPosition: Int) {
        helper.setText(R.id.tvVideoTitle, if (item.title == null) "" else item.title)
        helper.setText(R.id.tvSourceName_best, if (item.mediaName == null) "" else item.mediaName)

        GlideApp.with(mContext).load(if (item.pics[0] == null) "" else item.pics[0].url)
            .into(helper.getView<View>(R.id.img_video) as ShapeImageView)
        helper.addOnClickListener(R.id.flVideo)
        helper.setOnClickListener(
            R.id.flVideo
        ) { ToastUitl.showShort(if (item.title == null) "" else item.title) }
        helper.setText(R.id.tvDate, DateTimeUtils.getLongDateToString(item.virtualTime) + "")
        if (item.videos.size > 0) {
            helper.setText(R.id.tvTotalText, DateTimeUtils.formatVideoTime(item.videos[0].duration) + "")
        }

        val view = helper.getView<LinearLayout>(R.id.article_left_best)
        view.setOnClickListener { ToastUitl.showShort("视频：" + adapterPosition + item.title) }
    }


    /**
     * 上下滚动广告轮播
     *
     * @param helper
     * @param item
     * @param adapterPosition
     */
    private fun bindUpdownSwitcherData(helper: BaseViewHolder, item: HomeTophotIndex.Articles, adapterPosition: Int) {
        val viewSwitcher = helper.getView<UpDownViewSwitcher>(R.id.home_view_switcher)
        viewSwitcher.setSwitcheNextViewListener(UpDownViewSwitcher.SwitchNextViewListener { nextView, index ->
            if (nextView == null) return@SwitchNextViewListener
            val tag = "热"
            val tag1 = item.hotSpecialNews[index % item.hotSpecialNews.size].title
            (nextView.findViewById<View>(R.id.switch_title_status) as TextView).text = tag
            (nextView.findViewById<View>(R.id.switch_title) as TextView).text = tag1
            nextView.setOnClickListener { v ->
                satrtHotNewsBest()
            }
        })
        viewSwitcher.setContentLayout(R.layout.item_home_buttelin_switch_view)
    }


    /**
     * 横向分页九宫格菜单
     *
     * @param helper
     * @param item
     * @param adapterPosition
     */
    private fun bindHorizaontal9MenuData(helper: BaseViewHolder, item: HomeTophotIndex.Articles, adapterPosition: Int) {
        val mPager = helper.getView<ViewPager>(R.id.viewpager)
        val mLlDot = helper.getView<LinearLayout>(R.id.ll_dot)
        //填充数据
        addData()
        //设置数据
        setMixData(mPager, mLlDot)
    }


    /**
     * 视频-支持直接播放
     * @param helper
     * @param articles
     * @param adapterPosition
     */
    private fun bindVideoPlayerNewsData(helper: BaseViewHolder, articles: HomeTophotIndex.Articles, adapterPosition: Int) {
        val position = helper.adapterPosition
        val mVideoPlayer = helper.getView<JzvdStd>(R.id.videoplayers)
        if (mVideoPlayer != null) {
            val llVideoPlayer = helper.getView<LinearLayout>(R.id.llVideoPlayer)
            // 将列表中的每个视频设置为默认16:9的比例
            val params = mVideoPlayer.layoutParams
            // 宽度为屏幕宽度
            params.width = llVideoPlayer.resources.displayMetrics.widthPixels
            // 高度为宽度的9/16
            params.height = (params.width * 9f / 16f).toInt()
            mVideoPlayer.layoutParams = params
            bindData(mVideoPlayer, articles)
            helper.setText(R.id.videoChannel, if (articles.mediaName == null) "" else articles.mediaName)
            helper.setText(R.id.tvVideoCommentNum, articles.commentNum.toString() + "")
        }
    }

    /**
     * 单张大图
     *
     * @param helper
     * @param item
     * @param adapterPosition
     */
    private fun bindSingleBigNewsData(helper: BaseViewHolder, item: HomeTophotIndex.Articles, adapterPosition: Int) {
        val view = helper.getView<LinearLayout>(R.id.article_left_best)
        helper.setText(R.id.tvSourceName_best, if (item.mediaName == null) "" else item.mediaName)
        helper.setText(R.id.tvCommentnum_best, item.commentNum.toString() + "")
        helper.setText(R.id.tvArticletitle_best, if (item.title == null) "" else item.title)
        mImageLoader.displayImage(mContext, item.pics[0].url, helper.getView<ImageView>(R.id.img_single_big))
        view.setOnClickListener {
            satrtNewsDetail(item.articleUrl)
        }
    }



    private fun satrtNewsDetail(articleUrl: String?) {
        val mBundle = Bundle()
        mBundle.putString("articleUrl",articleUrl)
        mFragment.startProxyActivity(HotNewsDetailFragment::class.java,mBundle)
    }

    private fun satrtHotNewsBest() {
        mFragment.startProxyActivity(HotNewsBestFragment::class.java)
    }

    private fun addData() {
        mDatas = ArrayList<Menu9Model>()
        val titles = mContext.resources.getStringArray(R.array.menu9_title)
        for (i in titles.indices) {
            //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
            val imageId = mContext.resources.getIdentifier("ic_category_$i", "drawable", mContext.packageName)
            (mDatas as ArrayList<Menu9Model>).add(Menu9Model(titles[i], imageId))
        }
    }

    /**
     * 设置横向滑动分页九宫格菜单数据
     *
     * @param mPager
     * @param mLlDot
     */
    private fun setMixData(mPager: ViewPager?, mLlDot: LinearLayout?) {
        if (mPager != null && mLlDot != null) {
            inflater = LayoutInflater.from(mContext)
            //总的页数=总数/每页数量，并取整
            pageCount = ceil(mDatas!!.size * 1.0 / pageSize).toInt()
            mPagerList = ArrayList<View>()
            for (i in 0 until pageCount) {
                //每个页面都是inflate出一个新实例
                val gridView = inflater!!.inflate(R.layout.menu9_gridview, mPager, false) as GridView
                gridView.isFocusable = false
                gridView.isFocusableInTouchMode = false
                gridView.adapter = Home9MenuGridViewAdapter(mContext, mDatas, i, pageSize)
                mPagerList!!.add(gridView)

                gridView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                    val pos = position + curIndex * pageSize
                    ToastUitl.showShort(mDatas!![pos].getName())
                }
            }
            //设置适配器
            mPager.adapter = Menu9ViewPagerAdapter(mPagerList)
            //设置圆点
            setOvalLayout(mPager, mLlDot)
        }
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

    private fun initDragMoreLayout(horizontalDragMoreView: PulToLeftViewGroup?) {
        horizontalDragMoreView?.setOnPullToLeftListener(object : PulToLeftViewGroup.OnPullToLeftListener {
            override fun onReleaseFingerToUpload() {
                //回弹
                horizontalDragMoreView.completeToUpload()
                //                    HotNewsBestActivity.newInstance(context, null, AppConstant.HOT_NEWS_PARAMVALUE_TOP);
            }

            override fun onStartToUpload() {

            }
        })

        //            horizontalDragMoreView.setLoadMoreView(new DefaultDragMoreView()).setLoadMoreCallBack(new ICallBack() {
        //                @Override
        //                public void loadMore() {
        //                    //加载更多，或者跳转到其他页面等等操作
        //                    //加载更多数据模拟耗时操作
        //                    //非加载数据，如跳转则无需耗时线程操作
        //                    horizontalDragMoreView.postDelayed(new Runnable() {
        //                        @Override
        //                        public void run() {
        //                            //回弹
        //                            horizontalDragMoreView.scrollBackToOrigin();
        //                            HotNewsBestActivity.newInstance(context, null, AppConstant.HOT_NEWS_PARAMVALUE_TOP);
        //                        }
        //                    }, 1000);
        //
        //                }
        //            });
        //        }
    }

    /**
     * 设置圆点
     *
     * @param mPager
     * @param mLlDot
     */
    fun setOvalLayout(mPager: ViewPager, mLlDot: LinearLayout?) {
        mLlDot?.removeAllViews()
        for (i in 0 until pageCount) {
            mLlDot!!.addView(inflater!!.inflate(R.layout.menu9_dot, null))
        }
        // 默认显示第一页
        mLlDot!!.getChildAt(0).findViewById<View>(R.id.v_dot)
            .setBackgroundResource(R.drawable.dot_selected)
        mPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                // 取消圆点选中
                mLlDot.getChildAt(curIndex)
                    .findViewById<View>(R.id.v_dot)
                    .setBackgroundResource(R.drawable.dot_normal)
                // 圆点选中
                mLlDot.getChildAt(position)
                    .findViewById<View>(R.id.v_dot)
                    .setBackgroundResource(R.drawable.dot_selected)
                curIndex = position
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}

            override fun onPageScrollStateChanged(arg0: Int) {}
        })
    }


    private fun bindData(mVideoPlayer: JzvdStd?, articles: HomeTophotIndex.Articles) {
        if (mVideoPlayer != null && articles.videos.size > 0) {
            //播放带清晰度的视频
            val map = LinkedHashMap<String,String>()
            map.put("高清", articles.videos[0].url)
            map.put(
                "普清",
                if (articles.videos[0].definitionInfos.size >= 2) articles.videos[0].definitionInfos[0].url else articles.videos[0].url
            )
            map.put(
                "标清",
                if (articles.videos[0].definitionInfos.size >= 2) articles.videos[0].definitionInfos[1].url else articles.videos[0].url
            )
            val jzDataSource = JZDataSource(map, if (articles.title == null) "" else articles.title)
            jzDataSource.looping = true
            jzDataSource.currentUrlIndex = 2
            jzDataSource.headerMap.put("key", "value")//header
            mVideoPlayer.setUp(jzDataSource, JzvdStd.SCREEN_WINDOW_NORMAL)
//                        mVideoPlayer.setUp(articles.videos.get(0).url, articles.title == null ? "" : articles.title,Jzvd.SCREEN_WINDOW_NORMAL);
            val titleTextView = mVideoPlayer.titleTextView
            titleTextView.textSize = 13f
            titleTextView.maxLines = 2
            titleTextView.ellipsize = TextUtils.TruncateAt.END
            //粗体
            titleTextView.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            titleTextView.setPadding(0, 10, 0, 10)
            //            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) titleTextView.getLayoutParams();
            //            p.setMargins(10,50,10,10);
            //            titleTextView.requestLayout();
            //            titleTextView.setLayoutParams(p);
        }
        mVideoPlayer!!.thumbImageView.scaleType = ImageView.ScaleType.FIT_XY
        // 将列表中的每个视频设置为默认16:9的比例
        val params = mVideoPlayer.layoutParams
        // 宽度为屏幕宽度
        params.width = mContext.resources.displayMetrics.widthPixels
        // 高度为宽度的9/16
        params.height = (params.width * 9f / 16f).toInt()
        mVideoPlayer.layoutParams = params
        mImageLoader.displayImage(mContext, articles.pics[0].url, mVideoPlayer.thumbImageView)
    }

}