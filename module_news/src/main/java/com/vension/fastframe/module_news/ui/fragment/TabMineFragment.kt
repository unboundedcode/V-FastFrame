package com.vension.fastframe.module_news.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vension.fastframe.module_news.NewsConstant
import com.vension.fastframe.module_news.R
import com.vension.fastframe.module_news.bean.ProfileCareModel
import com.vension.fastframe.module_news.ui.adapter.ProfileAdapter
import com.vension.fastframe.module_news.utils.FileUtil
import kotlinx.android.synthetic.main.fragment_tab_mine_news.*
import kv.vension.vframe.core.AbsCompatFragment
import kv.vension.vframe.ext.Logi
import kv.vension.vframe.ext.showToast
import java.util.*

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/29 16:23.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/29 16:23
 * @desc:
 * ===================================================================
 */
class TabMineFragment : AbsCompatFragment() {

    companion object{
        fun newInstance() : TabMineFragment{
            return TabMineFragment()
        }
    }

    private var mAdapter: ProfileAdapter? = null
    private var profileList: MutableList<ProfileCareModel.Profile_data_list>? = null

    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_tab_mine_news
    }

    @SuppressLint("WrongConstant")
    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        if (profileList != null)
            profileList?.clear()
        else
            profileList = ArrayList()
        mAdapter = ProfileAdapter(activity!!, profileList!!)
        val layoutManager = LinearLayoutManager(mActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_Mine.layoutManager = layoutManager
        rv_Mine.adapter = mAdapter

        collapse_menu.setOnClickListener(this)
        header_menu.setOnClickListener(this)
    }

    override fun lazyLoadData() {
        //解析json
        val json = FileUtil.getJson(context, NewsConstant.ASSET_PROFILE_CARE)
        val multiIndexData = Gson().fromJson<ProfileCareModel>(json, object:TypeToken<ProfileCareModel>() {}.type)
        Logi(" getData: " + Gson().toJson(multiIndexData))
        if (multiIndexData != null) {
            val datas = multiIndexData.profile_data_list
            mAdapter!!.data.clear()
            if (datas != null) {
                mAdapter!!.setNewData(datas)
            } else {
                showToast("未查询到信息")
            }
        }
    }


    override fun onClick(v: View?) {
       when(v?.id){
           R.id.collapse_menu,R.id.header_menu ->{
               showToast("设置")
           }
       }
    }

}