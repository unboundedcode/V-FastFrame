package com.vension.fastframe.module_news.ui.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import com.vension.fastframe.module_news.NewsConstant
import com.vension.fastframe.module_news.R
import com.vension.fastframe.module_news.bean.CategoryModel
import com.vension.fastframe.module_news.widget.ColorFlipPagerTitleView
import kotlinx.android.synthetic.main.fragment_tab_headnews.*
import kv.vension.vframe.core.AbsCompatFragment
import kv.vension.vframe.core.adapter.BaseFragmentAdapter
import kv.vension.vframe.utils.StatusBarUtil
import lib.vension.fastframe.common.test.TestFragment
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import java.util.*

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/30 17:08.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/30 17:08
 * @desc:
 * ===================================================================
 */
class TabHeadNewsFragment : AbsCompatFragment() {

    private var modelList = ArrayList<CategoryModel>()
    private var mFragmentList = ArrayList<Fragment>()

    companion object{
        fun newInstance() : TabHeadNewsFragment{
            return TabHeadNewsFragment()
        }
    }


    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_tab_headnews
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        modelList?.addAll(NewsConstant.getFrontNewsCategory())
        initPageView()
        initMagicIndicator7()
    }


    private fun initPageView() {
        val channelNames = ArrayList<String>()
        for (model in modelList) {
            channelNames.add(model.title)
        }
        if (modelList != null) {
            setNewsList(modelList, channelNames)
            setViewPager(channelNames)
        }
    }

    override fun lazyLoadData() {

    }


    /**
     * 设置数据列表
     *
     * @param modelList
     * @param channelNames
     */
    private fun setNewsList(modelList: List<CategoryModel>, channelNames: MutableList<String>) {
        mFragmentList.clear()
        for (i in modelList.indices) {
            if (i == 0) {
                val frontNewsMixMainFragment = HeadRecommendFragment()
                val bundle = Bundle()
                frontNewsMixMainFragment.arguments = bundle
                mFragmentList.add(frontNewsMixMainFragment)
                channelNames.add(modelList[i].title)
            } else if (i == 1) {
                val mineCategoryFragment = MyHeadFollowFragment()
                val bundle = Bundle()
                mineCategoryFragment.arguments = bundle
                mFragmentList.add(mineCategoryFragment)
                channelNames.add(modelList[i].title)
            } else {
                val movieCategoryFragment = TestFragment()
                val bundle = Bundle()
                movieCategoryFragment.arguments = bundle
                mFragmentList.add(movieCategoryFragment)
                channelNames.add(modelList[i].title)
            }
        }
    }

    /**
     * 初始化ViewPager
     *
     * @param channelNames
     */
    private fun setViewPager(channelNames: List<String>) {
        val adapter = BaseFragmentAdapter(childFragmentManager,mFragmentList, channelNames )
        vp_tabHead.offscreenPageLimit = mFragmentList.size
        vp_tabHead.adapter = adapter
    }


    private fun initMagicIndicator7() {
        val commonNavigator7 = CommonNavigator(activity)
        commonNavigator7.scrollPivotX = 0.65f
        commonNavigator7.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return if (modelList == null) 0 else modelList.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = ColorFlipPagerTitleView(context)
                simplePagerTitleView.text = modelList[index].title
                simplePagerTitleView.normalColor = R.color.colorNewsMain
                simplePagerTitleView.selectedColor = Color.parseColor("#D83D3A")
                simplePagerTitleView.setOnClickListener(View.OnClickListener {
                    vp_tabHead.currentItem = index
                })
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                /**
                 * 指示器的高度
                 */
                indicator.lineHeight = UIUtil.dip2px(context, 3.0).toFloat()
                indicator.lineWidth = UIUtil.dip2px(context, 10.0).toFloat() //  指示器的宽度
                indicator.roundRadius = UIUtil.dip2px(context, 3.0).toFloat()//  指示器的圆角
                indicator.startInterpolator = AccelerateInterpolator()
                indicator.endInterpolator = DecelerateInterpolator(2.0f)
                indicator.setColors(Color.parseColor("#D83D3A"))
                return indicator
            }
        }
        magicIndicator.navigator = commonNavigator7
        ViewPagerHelper.bind(magicIndicator, vp_tabHead)
    }



    override fun onFragmentVisble() {
        super.onFragmentVisble()
        StatusBarUtil.setLightMode(activity!!)
    }


}