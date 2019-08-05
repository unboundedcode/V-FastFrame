package com.vension.fastframe.module_course.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.vension.fastframe.module_course.R
import com.vension.fastframe.module_course.bean.DiscoveryCommentBean
import com.vension.fastframe.module_course.bean.TagBean
import com.vension.fastframe.module_course.event.RefreshHomeEvent
import com.vension.fastframe.module_course.mvp.contract.ChoosePhaseContract
import com.vension.fastframe.module_course.mvp.presenter.ChoosePhasePresenter
import com.vension.fastframe.module_course.ui.adapter.ChoosePhaseAdapter
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.common_tag.view.*
import kotlinx.android.synthetic.main.fragment_select_phase.*
import kv.vension.vframe.VFrame
import kv.vension.vframe.core.mvp.AbsCompatMVPFragment
import org.greenrobot.eventbus.EventBus

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/26 14:04.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/26 14:04
 * @desc:
 * ===================================================================
 */
class ChoosePhaseFragment : AbsCompatMVPFragment<ChoosePhaseContract.View, ChoosePhasePresenter>(),ChoosePhaseContract.View {

    private lateinit var mStages: DiscoveryCommentBean.Stages
    private lateinit var numbers: ArrayList<Int>
    private lateinit var mTagBean: TagBean
    private var leftId = 0
    private var mPosition = -1
    private val mAdapter: ChoosePhaseAdapter by lazy {
        ChoosePhaseAdapter(R.layout.choose_left_item, null)
    }

    override fun createPresenter(): ChoosePhasePresenter {
        return ChoosePhasePresenter()
    }

    override fun attachLayoutRes(): Int {
     return R.layout.fragment_select_phase
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar,title:String) {
        super.initToolBar(mCommonTitleBar,"选择学习阶段")
        mCommonTitleBar.setBackgroundColor(VFrame.getColor(R.color.colorCourseMain))
    }
    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            numbers = it.getIntegerArrayList("number") as ArrayList<Int>
        }

        mAdapter.setOnItemChildClickListener { _, _, position ->
            leftId = mStages.`154271985`.subTags[position].tagId
            mPosition = -1
            switchScreen()
        }
    }

    override fun lazyLoadData() {
        mPresenter.getDiscoveryComment()
    }

    @SuppressLint("WrongConstant")
    override fun showDiscoveryComment(mData: DiscoveryCommentBean) {
        mStages = mData.stages
        mAdapter.addData(mStages.`154271985`.subTags)
        left.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        left.adapter = mAdapter
        leftId = numbers[0]
        switchScreen()
    }

    private fun switchScreen() {
        val hostObject = JsonParser().parse(Gson().toJson(mStages)).asJsonObject
        var nextId: String? = null
        loop@ for (stages in mStages.`154271985`.subTags) {
            if (leftId == stages.tagId) {
                nextId = stages.nextStage
                switchCategory(mStages.`154271985`.subTags.indexOf(stages))
                break@loop
            }
        }
        loop@ for (article in hostObject.keySet()) {
            if (nextId == article) {
                val json = hostObject.getAsJsonObject(article)
                mTagBean = Gson().fromJson(json, TagBean::class.java)
                second@ for (tag in mTagBean.subTags) {
                    if (tag.tagId == numbers[1]) {
                        mPosition = mTagBean.subTags.indexOf(tag)
                        break@second
                    }
                }
                switchRepresent(mTagBean)
                break@loop
            }
        }
    }

    private fun switchRepresent(mTagBean: TagBean) {
        val titles = arrayListOf<String>()
        val tagId = arrayListOf<Int>()
        for (message in mTagBean.subTags) {
            titles.add(message.tagName)
            tagId.add(message.tagId)
        }
        initTagLayout(titles, tagId)
    }

    private fun initTagLayout(titles: List<String>, tagId: List<Int>) {
        mTflRepresent.adapter = object : TagAdapter<String>(titles) {
            override fun getView(parent: FlowLayout?, position: Int, title: String?): View {
                val mTagLayout = LayoutInflater.from(mContext)
                    .inflate(R.layout.common_tag, parent, false)
                mTagLayout.mTvTag.text = title
                if (position == mPosition) {
                    mTagLayout.mTvTag.setTextColor(VFrame.getColor(R.color.tab_textSelectColor))
                    mTagLayout.cardView.setCardBackgroundColor(VFrame.getColor(R.color.bg_green))
                } else {
                    mTagLayout.mTvTag.setTextColor(VFrame.getColor(R.color.bottom_textUnSelectColor))
                    mTagLayout.cardView.setCardBackgroundColor(VFrame.getColor(R.color.white))
                }
                return mTagLayout
            }
        }

        mTflRepresent.setOnTagClickListener { _, position, _ ->
            EventBus.getDefault().post(RefreshHomeEvent(true, leftId,tagId[position]))
            activity!!.finish()
            true
        }
    }

    private fun switchCategory(position: Int) {
        mAdapter.selectedPosition = position
        mAdapter.notifyDataSetChanged()
    }

}