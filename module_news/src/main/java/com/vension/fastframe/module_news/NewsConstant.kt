package com.vension.fastframe.module_news

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vension.fastframe.module_news.bean.CategoryModel
import com.vension.fastframe.module_news.bean.HomeNewsTabBean
import com.vension.fastframe.module_news.bean.NewsChannelTable
import com.vension.fastframe.module_news.utils.FileUtil
import kv.vension.vframe.VFrame
import java.util.*

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/29 17:37.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/29 17:37
 * @desc:
 * ===================================================================
 */
object NewsConstant {

    const val HOME_MENU_URL_A = "https://img1.epetbar.com/2018-10/19/19/9aa905ec1644bf2773a451aca9af06d3.png@!water"
    const val HOME_MENU_URL_B = "https://img1.epetbar.com/2018-10/19/18/ed8fcf7e8b283838a02a6a1604cb0237.png@!water"
    const val HOME_MENU_URL_C = "https://img1.epetbar.com/2018-10/19/18/5156506734c2d1c2a8fbbaa74fae5494.png@!water"
    const val HOME_MENU_URL_D = "https://img1.epetbar.com/2018-10/19/18/8faa333a45eb95384551b712866e91ff.png@!water"
    const val HOME_MENU_URL_E = "https://img1.epetbar.com/2018-10/18/20/806c70e41b42268892244372dfe393f8.png@!water"

    /**
     * 加载样式标记
     */
    var FLAG_NEWS = 1
    /**
     * 下拉刷新数据状态
     */
    const val SUCCESS = 1
    const val FAILED = 0

    const val HOME_TAB_SELECTED_SIZE = 20f
    const val HOME_TAB_UNSELECTED_SIZE = 12f

    const val CHANNEL_SWAP = "CHANNEL_SWAP"
    const val CHANNEL_MINE = "CHANNEL_MINE"
    const val CHANNEL_MORE = "CHANNEL_MORE"

    /**
     * 要闻状态标记
     */
    const val FRONT_NEWS_RIGHT_BOTTOM_RIGHTNOW = 0
    const val FRONT_NEWS_WAIT_PUBLISH = 1
    const val FRONT_NEWS_ALRIGHT_PUBLISH = 2

    //头条
    var ASSET_HOME_HOT_TOP = "imp_home_hot.json"
    //热闻精选
    var ASSET_HOT_NEWS_BEST = "hotnews_best_page.json"
    var ASSET_HOT_NEWS_BEST_MORE = "hotnews_best_page_more.json"
    //要闻
    var ASSET_HOME_IMPORTANT = "home_news_important.json"
    //视频
    var ASSET_HOME_VIDEO = "home_news_video.json"

    //HOEM-TAB
    var ASSET_HOME_NEWS_TABS = "home_news_tabs.json"

    //要闻
    var ASSET_FRONT_NEWS = "frontnews_load.json"
    var ASSET_FRONT_NEWS_MORE = "frontnews_load_more.json"

    //<-------------------视频-------------->
    //推荐
    var ASSET_VIDEO_NEWS_TUIJIAN = "video_tuijian_load.json"
    var ASSET_VIDEO_NEWS_TUIJIAN_MORE = "video_tuijian_load_more.json"
    //搞笑
    var ASSET_VIDEO_NEWS_GAOXIAO = "video_gaoxiao_load.json"
    var ASSET_VIDEO_NEWS_GAOXIAO_MORE = "video_gaoxiao_load_more.json"
    //萌宠
    var ASSET_VIDEO_NEWS_MENGPET = "video_mengpet_load.json"
    var ASSET_VIDEO_NEWS_MENGPET_MORE = "video_mengpet_load_more.json"
    //美食
    var ASSET_VIDEO_NEWS_FOOD = "video_food_load.json"
    var ASSET_VIDEO_NEWS_FOOD_MORE = "video_food_load_more.json"
    //军事
    var ASSET_VIDEO_NEWS_JUNSHI = "video_junshi_load.json"
    var ASSET_VIDEO_NEWS_JUNSHI_MORE = "video_junshi_load_more.json"
    //音乐
    var ASSET_VIDEO_NEWS_MUSIC = "video_music_load.json"
    var ASSET_VIDEO_NEWS_MUSIC_MORE = "video_music_load_more.json"
    //娱乐
    var ASSET_VIDEO_NEWS_YULE = "video_yule_load.json"
    var ASSET_VIDEO_NEWS_YULE_MORE = "video_yule_load_more.json"
    //育儿
    var ASSET_VIDEO_NEWS_YUER = "video_yuer_load.json"
    var ASSET_VIDEO_NEWS_YUER_MORE = "video_yuer_load_more.json"
    //影视
    var ASSET_VIDEO_NEWS_MOVIE = "video_movie_load.json"
    var ASSET_VIDEO_NEWS_MOVIE_MORE = "video_movie_load_more.json"
    //科技
    var ASSET_VIDEO_NEWS_KEJI = "video_keji_load.json"
    var ASSET_VIDEO_NEWS_KEJI_MORE = "video_keji_load_more.json"
    //小品
    var ASSET_VIDEO_NEWS_XIAOPIN = "video_xiaopin_load.json"
    var ASSET_VIDEO_NEWS_XIAOPIN_MORE = "video_xiaopin_load_more.json"
    //舞蹈
    var ASSET_VIDEO_NEWS_DANCES = "video_dances_load.json"
    var ASSET_VIDEO_NEWS_DANCES_MORE = "video_dances_load_more.json"
    //<-------------------视频----END.---------->

    //<-------------------我的----END.---------->
    const val ASSET_PROFILE_CARE = "profile_care_list.json"

    /**
     * 首页
     * 获取当前页面初始刷新数据来源的标识文件名
     * @param currentLoadPos 当前页面位置
     * @return
     */
    fun getHomeRefreshTypeSource(currentLoadPos: Int): String {
        var jsonNameStr = ""
        jsonNameStr = when (currentLoadPos) {
            0 -> NewsConstant.ASSET_HOME_HOT_TOP
            1 -> NewsConstant.ASSET_HOME_IMPORTANT
            2//视频
            -> NewsConstant.ASSET_HOME_VIDEO
            3 -> NewsConstant.ASSET_HOME_IMPORTANT
            4 -> NewsConstant.ASSET_HOME_IMPORTANT
            5 -> NewsConstant.ASSET_HOME_IMPORTANT
            6 -> NewsConstant.ASSET_HOME_IMPORTANT
            7 -> NewsConstant.ASSET_HOME_IMPORTANT
            8 -> NewsConstant.ASSET_HOME_IMPORTANT
            else -> NewsConstant.ASSET_HOME_IMPORTANT
        }
        return jsonNameStr
    }


    /**
     * 首页
     * 获取当前页面加载更多数据来源的标识文件名
     * @param currentLoadMorePos 当前页面位置
     * @return
     */
    fun getHomeLoadMoreTypeSource(currentLoadMorePos: Int): String {
        var jsonNameStr = ""
        jsonNameStr = when (currentLoadMorePos) {
            0 -> NewsConstant.ASSET_HOME_HOT_TOP
            1 -> NewsConstant.ASSET_HOME_IMPORTANT
            2 -> NewsConstant.ASSET_HOME_VIDEO
            3 -> NewsConstant.ASSET_HOME_IMPORTANT
            4 -> NewsConstant.ASSET_HOME_IMPORTANT
            5 -> NewsConstant.ASSET_HOME_IMPORTANT
            6 -> NewsConstant.ASSET_HOME_IMPORTANT
            7 -> NewsConstant.ASSET_HOME_IMPORTANT
            8 -> NewsConstant.ASSET_HOME_IMPORTANT
            else -> NewsConstant.ASSET_HOME_IMPORTANT
        }
        return jsonNameStr
    }



    /**
     * 加载固定新闻类型
     * @return
     */
    fun loadNewsChannelsStatic(): List<NewsChannelTable> {
        val channelName =
            listOf(VFrame.getResources().getStringArray(R.array.news_channel_name_static))
        val channelId =
            listOf(VFrame.getResources().getStringArray(R.array.news_channel_id_static))
        val newsChannelTables = ArrayList<NewsChannelTable>()
        for (i in channelName.indices) {
            val entity = NewsChannelTable(
                channelName[i].toString(),
                channelId[i].toString(),
                getType(channelId[i].toString()),
                i <= 5,
                i,
                true
            )
            newsChannelTables.add(entity)
        }
        return newsChannelTables
    }

    // 头条id
    val HEADLINE_ID = "T1348647909107"
    // 房产id
    val HOUSE_ID = "5YyX5Lqs"
    // 足球
    val FOOTBALL_ID = "T1399700447917"
    // 娱乐
    val ENTERTAINMENT_ID = "T1348648517839"
    // 体育
    val SPORTS_ID = "T1348649079062"
    // 财经
    val FINANCE_ID = "T1348648756099"
    // 科技
    val TECH_ID = "T1348649580692"
    // 电影
    val MOVIE_ID = "T1348648650048"
    // 汽车
    val CAR_ID = "T1348654060988"
    // 笑话
    val JOKE_ID = "T1350383429665"
    // 游戏
    val GAME_ID = "T1348654151579"
    // 时尚
    val FASHION_ID = "T1348650593803"
    // 情感
    val EMOTION_ID = "T1348650839000"
    // 精选
    val CHOICE_ID = "T1370583240249"
    // 电台
    val RADIO_ID = "T1379038288239"
    // nba
    val NBA_ID = "T1348649145984"
    // 数码
    val DIGITAL_ID = "T1348649776727"
    // 移动
    val MOBILE_ID = "T1351233117091"
    // 彩票
    val LOTTERY_ID = "T1356600029035"
    // 教育
    val EDUCATION_ID = "T1348654225495"
    // 论坛
    val FORUM_ID = "T1349837670307"
    // 旅游
    val TOUR_ID = "T1348654204705"
    // 手机
    val PHONE_ID = "T1348649654285"
    // 博客
    val BLOG_ID = "T1349837698345"
    // 社会
    val SOCIETY_ID = "T1348648037603"
    // 家居
    val FURNISHING_ID = "T1348654105308"
    // 暴雪游戏
    val BLIZZARD_ID = "T1397016069906"
    // 亲子
    val PATERNITY_ID = "T1397116135282"
    // CBA
    val CBA_ID = "T1348649475931"
    // 消息
    val MSG_ID = "T1371543208049"
    // 军事
    val MILITARY_ID = "T1348648141035"


    // 头条TYPE
    val HEADLINE_TYPE = "headline"
    // 房产TYPE
    val HOUSE_TYPE = "house"
    // 其他TYPE
    val OTHER_TYPE = "list"


    /**
     * 新闻id获取类型
     *
     * @param id 新闻id
     * @return 新闻类型
     */
    fun getType(id: String): String {
        when (id) {
            HEADLINE_ID -> return HEADLINE_TYPE
            HOUSE_ID -> return HOUSE_TYPE
            else -> {
            }
        }
        return OTHER_TYPE
    }

    /**
     * 获取默认tab标签列表
     * @param tabType 0:我的  1:更多
     * @param context context
     * @return
     */
    fun getTabCurrentShowData(tabType: Int, context: Context): List<HomeNewsTabBean.Data.My>? {
        val multiIndexJsonData = getMultiIndexJsonData(NewsConstant.ASSET_HOME_NEWS_TABS) as HomeNewsTabBean
        if (multiIndexJsonData != null) {
            if (tabType == 0) {
                return multiIndexJsonData!!.data.my
            }
        }
        return null
    }

    /**
     * 要闻分类
     * @param mContext
     * @return
     */
    fun getFrontNewsCategory(): ArrayList<CategoryModel> {
        val list = ArrayList<CategoryModel>()
        list.add(CategoryModel(VFrame.getString(R.string.news_head)))
        list.add(CategoryModel(VFrame.getString(R.string.news_head_my)))
        return list
    }

    /**
     * 视频随机获取分类
     * @param mContext
     * @return
     */
    fun getVideoCategoryStr(): List<String> {
        val list = ArrayList<String>()
        list.add(VFrame.getString(R.string.txt_type2))
        list.add(VFrame.getString(R.string.txt_type3))
        list.add(VFrame.getString(R.string.txt_type4))
        list.add(VFrame.getString(R.string.txt_type5))
        list.add(VFrame.getString(R.string.txt_type6))
        list.add(VFrame.getString(R.string.txt_type7))
        list.add(VFrame.getString(R.string.txt_type8))
        list.add(VFrame.getString(R.string.txt_type9))
        list.add(VFrame.getString(R.string.txt_type10))
        list.add(VFrame.getString(R.string.txt_type11))
        list.add(VFrame.getString(R.string.txt_type12))
        list.add(VFrame.getString(R.string.txt_type13))
        return list
    }

    /**
     * 获取当前页面初始刷新数据来源的标识文件名
     * @param currentLoadMorePos 当前页面位置
     * @return
     */
    fun getVideoRefreshTypeSource(currentLoadMorePos: Int): String {
        var jsonNameStr = ""
        when (currentLoadMorePos) {
            0 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_TUIJIAN
            1 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_GAOXIAO
            2 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_MENGPET
            3 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_FOOD
            4 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_JUNSHI
            5 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_MUSIC
            6 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_YULE
            7 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_YUER
            8 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_MOVIE
            9 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_KEJI
            10 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_XIAOPIN
            11 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_DANCES
            else -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_TUIJIAN
        }
        return jsonNameStr
    }


    /**
     * 获取当前页面加载更多数据来源的标识文件名
     * @param currentLoadMorePos 当前页面位置
     * @return
     */
    fun getVideoLoadMoreTypeSource(currentLoadMorePos: Int): String {
        var jsonNameStr = ""
        when (currentLoadMorePos) {
            0 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_TUIJIAN_MORE
            1 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_GAOXIAO_MORE
            2 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_MENGPET_MORE
            3 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_FOOD_MORE
            4 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_JUNSHI_MORE
            5 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_MUSIC_MORE
            6 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_YULE_MORE
            7 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_YUER_MORE
            8 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_MOVIE_MORE
            9 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_KEJI_MORE
            10 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_XIAOPIN_MORE
            11 -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_DANCES_MORE
            else -> jsonNameStr = NewsConstant.ASSET_VIDEO_NEWS_TUIJIAN_MORE
        }
        return jsonNameStr
    }


    /**
     * 解析json
     */
    private fun getMultiIndexJsonData(fileName: String): HomeNewsTabBean {
        val json = FileUtil.getJson(VFrame.getContext(), fileName)
        val gson = Gson()
        val type = object : TypeToken<HomeNewsTabBean>() {}.type
        return gson.fromJson(json, type)
    }


}