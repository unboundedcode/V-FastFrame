package com.vension.fastframe.app.ui.tab1

import android.graphics.Color
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.fastframe.app.R
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import kv.vension.fastframe.ext.showToast
import kv.vension.fastframe.views.gridpager.GridPager
import lib.vension.fastframe.common.test.TestBean
import lib.vension.fastframe.common.utils.GlideImageLoader


/**
 * ===================================================================
 * @author: Created by Vension on 2019/8/2 13:08.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/8/2 13:08
 * @desc:
 * ===================================================================
 */
class TabAdapter_1(data: MutableList<TestBean>?) : BaseMultiItemQuickAdapter<TestBean, BaseViewHolder>(data) {

    init {
        addItemType(0, R.layout.item_rv_tab_home_banner)
        addItemType(1, R.layout.item_rv_tab_home_viewfilpper)
        addItemType(2, R.layout.item_rv_tab_home_gridpager_menu)
        addItemType(3, R.layout.item_rv_tab_home_three_menu)
        addItemType(5, R.layout.item_rv_tab_home)
    }

    override fun convert(helper: BaseViewHolder, item: TestBean) {
        val adapterPosition = helper.adapterPosition
        when (item?.itemType) {
            0 -> {//banner
                bindBanner(helper, item, adapterPosition)
            }
            1 -> {//viewFilpper
                bindTypeViewFilpper(helper, item, adapterPosition)
            }
            2 -> {//网格菜单
                bindGridPager(helper, item, adapterPosition)
            }
            3 -> {//3图菜单

            }
            5 -> {//普通item

            }
        }
    }


    private fun bindBanner(helper: BaseViewHolder, item: TestBean, pos: Int) {
        val mBanner = helper.getView<Banner>(R.id.homeBanner)
        val banners = listOf(R.drawable.banner_1,R.drawable.banner_2,R.drawable.banner_3,R.drawable.banner_4,R.drawable.banner_5)
        //默认是NUM_INDICATOR_TITLE
        mBanner.setImages(banners)
            .setImageLoader(GlideImageLoader())
            .setOnBannerListener(object : OnBannerListener {
                override fun OnBannerClick(position: Int) {
                    showToast("点击了第" + (position + 1) + "张banner")
                }
            })
            .setIndicatorGravity(Gravity.CENTER_HORIZONTAL)
            //设置指示器位置（当banner模式中有指示器时）
            .setIndicatorGravity(BannerConfig.CENTER)
            .setDelayTime(3000)
            .start()
    }

    private fun bindTypeViewFilpper(helper: BaseViewHolder, item: TestBean, adapterPosition: Int) {
        var mStrList = mutableListOf<String>()
        for (i in 1..15) {
            mStrList.add("这是第" + i + "个元素")
        }

        val viewFlipper = helper.getView<ViewFlipper>(R.id.viewFlipper)
        for ((index, str) in mStrList.withIndex()) {
            val textView = TextView(mContext)
            textView.text = str
            if (0 == index % 2) {
                textView.setTextColor(Color.RED)
            } else {
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.color_666666))
            }
            textView.setOnClickListener {
                showToast("str")
            }
            viewFlipper.addView(textView)
        }
    }

    private fun bindGridPager(helper: BaseViewHolder, item: TestBean, pos: Int) {
        val gridPager = helper.getView<GridPager>(R.id.gridPager)
        val titles = arrayOf(
            "美食",
            "电影",
            "酒店住宿",
            "休闲娱乐",
            "外卖",
            "自助餐",
            "KTV",
            "机票/火车票",
            "周边游",
            "美甲美睫",
            "火锅",
            "生日蛋糕",
            "甜品饮品",
            "水上乐园",
            "汽车服务",
            "美发",
            "丽人",
            "景点",
            "足疗按摩",
            "运动健身",
            "健身",
            "超市",
            "买菜",
            "今日新单",
            "小吃快餐",
            "面膜",
            "洗浴/汗蒸",
            "母婴亲子",
            "生活服务",
            "婚纱摄影",
            "学习培训",
            "家装",
            "结婚",
            "全部分配"
        )
        val iconS = IntArray(titles.size)
        for (i in titles.indices) {
            //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
            val imageId = mContext.resources.getIdentifier("ic_category_$i", "drawable", mContext.packageName)
            iconS[i] = imageId
        }
        gridPager
            .setDataAllCount(titles.size)
            .setItemBindDataListener(object : GridPager.ItemBindDataListener{
                override fun bindData(imageView: ImageView, textView: TextView, position: Int) {
                    // 自己进行数据的绑定，灵活度更高，不受任何限制
                    imageView!!.setImageResource(iconS[position])
                    textView!!.text = titles[position]
                }

            })
            .setGridItemClickListener(object : GridPager.GridItemClickListener{
                override fun onItemClick(position: Int) {
                    Toast.makeText(
                        mContext,
                        "点击了" + titles[position],
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
            .show()
    }


}