package com.vension.fastframe.module_news.ui.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.fastframe.module_news.R
import com.vension.fastframe.module_news.bean.ProfileCareModel
import com.vension.fastframe.module_news.bean.ProfileServiceGridBean
import kv.vension.fastframe.ext.showToast
import kv.vension.fastframe.glide.GlideApp
import kv.vension.fastframe.views.AtMostGridView
import java.util.*

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/29 17:10.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/29 17:10
 * @desc:
 * ===================================================================
 */
class ProfileAdapter(private val context: Context, data: List<ProfileCareModel.Profile_data_list>) :
    BaseMultiItemQuickAdapter<ProfileCareModel.Profile_data_list, BaseViewHolder>(data) {
    init {
        addItemType(ProfileCareModel.Profile_data_list.PROFILE_MY_TOPBAR, R.layout.item_profile_my_top_bar)
        addItemType(ProfileCareModel.Profile_data_list.PROFILE_MY_ORDER_MENU, R.layout.item_profile_my_order)
        addItemType(ProfileCareModel.Profile_data_list.PROFILE_MY_WALLET, R.layout.item_profile_my_wallet)
        addItemType(ProfileCareModel.Profile_data_list.PROFILE_MY_SERVICE, R.layout.item_profile_my_service)
        addItemType(ProfileCareModel.Profile_data_list.PROFILE_CONTACT_US, R.layout.item_profile_my_wallet)
    }



    protected override fun convert(baseViewHolder: BaseViewHolder, profile_data_list: ProfileCareModel.Profile_data_list) {
        when (profile_data_list.itemType) {
            ProfileCareModel.Profile_data_list.PROFILE_MY_TOPBAR -> { }
            ProfileCareModel.Profile_data_list.PROFILE_MY_ORDER_MENU -> {
                bindOrderMenuData(baseViewHolder, profile_data_list)
            }
            ProfileCareModel.Profile_data_list.PROFILE_MY_WALLET -> {
                bindWalletData(baseViewHolder, profile_data_list)
            }
            ProfileCareModel.Profile_data_list.PROFILE_MY_SERVICE -> {
                bindServiceData(baseViewHolder, profile_data_list)
            }
            ProfileCareModel.Profile_data_list.PROFILE_CONTACT_US -> {
                bindContactUsData(baseViewHolder, profile_data_list)
            }
            else -> {
            }
        }
    }


    /**
     * 我的订单
     *
     * @param holder
     * @param data
     */
    private fun bindOrderMenuData(holder: BaseViewHolder, data: ProfileCareModel.Profile_data_list?) {
        val linearLayouts = ArrayList<LinearLayout>()
        linearLayouts?.clear()
        val civTitle = holder.getView(R.id.civTitle) as AppCompatImageView
        linearLayouts.add(holder.getView(R.id.llOrder1) as LinearLayout)
        linearLayouts.add(holder.getView(R.id.llOrder2) as LinearLayout)
        linearLayouts.add(holder.getView(R.id.llOrder3) as LinearLayout)
        linearLayouts.add(holder.getView(R.id.llOrder4) as LinearLayout)
        linearLayouts.add(holder.getView(R.id.llOrder5) as LinearLayout)
        if (data != null && linearLayouts != null) {
            GlideApp.with(context).load(if (data!!.title_url == null) "" else data!!.title_url).into(civTitle)
            for (i in linearLayouts.indices) {
                val ico = (linearLayouts[i] as ViewGroup).getChildAt(0) as AppCompatImageView
                val name = (linearLayouts[i] as ViewGroup).getChildAt(1) as TextView
                name.text = if (data!!.item_list.get(i).below_text == null) "" else data!!.item_list.get(i).below_text
                GlideApp.with(context).load(if (data!!.item_list[i].above_image.img_url == null) "" else data!!.item_list[i].above_image.img_url).into(ico)
                linearLayouts[i].setOnClickListener { showToast(data!!.item_list[i].below_text + "") }
            }
        }
    }

    /**
     * 我的钱包
     * @param holder
     * @param data
     */
    private fun bindWalletData(holder: BaseViewHolder, data: ProfileCareModel.Profile_data_list?) {
        val civTitle = holder.getView(R.id.civTitle) as AppCompatImageView
        val linearLayouts = ArrayList<LinearLayout>()
        linearLayouts?.clear()
        linearLayouts.add(holder.getView(R.id.llWallet1) as LinearLayout)
        linearLayouts.add(holder.getView(R.id.llWallet2) as LinearLayout)
        linearLayouts.add(holder.getView(R.id.llWallet3) as LinearLayout)
        linearLayouts.add(holder.getView(R.id.llWallet4) as LinearLayout)
        if (data != null && linearLayouts != null) {
            GlideApp.with(context).load(if (data!!.title_url == null) "" else data!!.title_url).into(civTitle)
            for (i in linearLayouts.indices) {
                val num = (linearLayouts[i] as ViewGroup).getChildAt(0) as TextView
                val name = (linearLayouts[i] as ViewGroup).getChildAt(1) as TextView
                num.text = if (data!!.item_list.get(i).above_text == null) "" else data!!.item_list.get(i).above_text
                name.text = if (data!!.item_list.get(i).below_text == null) "" else data!!.item_list.get(i).below_text
                linearLayouts[i].setOnClickListener { showToast(data!!.item_list.get(i).below_text + "") }
            }
        }
    }

    /**
     * 我的服务
     *
     * @param holder
     * @param data
     */
    private fun bindServiceData(holder: BaseViewHolder, data: ProfileCareModel.Profile_data_list?) {
        val profileServiceGridBeans = ArrayList<ProfileServiceGridBean>()
        if (profileServiceGridBeans != null) profileServiceGridBeans.clear()
        val civTitle = holder.getView(R.id.civTitle) as AppCompatImageView
        val mySrviceGrid = holder.getView(R.id.mySrviceGrid) as AtMostGridView
        if (data != null) {
            GlideApp.with(context).load(if (data!!.title_url == null) "" else data!!.title_url).into(civTitle)
            if (data!!.item_list != null) {
                for (a in data!!.item_list) {
                    profileServiceGridBeans.add(ProfileServiceGridBean(a.above_image.img_url, a.below_text))
                }
            }
            mySrviceGrid.adapter = ProfileServiceGridViewAdapter(context, profileServiceGridBeans)
            mySrviceGrid.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                when (position) {
                    500 -> {
                    }
                    else ->  showToast(profileServiceGridBeans.get(position).titleName + "")
                }
            }
        }
    }

    /**
     * 联系我们
     *
     * @param holder
     * @param data
     */
    private fun bindContactUsData(holder: BaseViewHolder, data: ProfileCareModel.Profile_data_list) {}


}
