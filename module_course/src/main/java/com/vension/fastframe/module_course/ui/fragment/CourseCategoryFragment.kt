package com.vension.fastframe.module_course.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.fastframe.module_course.Constant
import com.vension.fastframe.module_course.R
import com.vension.fastframe.module_course.bean.VideoBean
import com.vension.fastframe.module_course.bean.VideoGroup
import com.vension.fastframe.module_course.mvp.contract.CourseCategoryContract
import com.vension.fastframe.module_course.mvp.presenter.CourseCategoryPresenter
import com.vension.fastframe.module_course.ui.adapter.CourseCategoryAdapter
import kv.vension.vframe.VFrame
import kv.vension.vframe.core.mvp.AbsCompatMVPRefreshFragment
import lib.vension.fastframe.common.RouterConfig

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/25 15:21.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/25 15:21
 * @desc:
 * ===================================================================
 */
class CourseCategoryFragment : AbsCompatMVPRefreshFragment<VideoBean,CourseCategoryContract.View,CourseCategoryPresenter>(),CourseCategoryContract.View {


    companion object {
        fun newInstance(id: Int): CourseCategoryFragment {
            val fragment = CourseCategoryFragment()
            val bundle = Bundle()
            bundle.putInt(Constant.EXTRA_POSITION, id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun enableLoadMore(): Boolean {
        return false
    }

    override fun createRecyItemDecoration(): RecyclerView.ItemDecoration? {
        return null
    }

    override fun getRefreshColor(color: Int): Int {
        return VFrame.getColor(R.color.colorCourseMain)
    }

    override fun createRecyAdapter(): BaseQuickAdapter<VideoBean, BaseViewHolder> {
       return CourseCategoryAdapter()
    }

    override fun addItemClickListener(mAdapter: BaseQuickAdapter<VideoBean, BaseViewHolder>) {
        mAdapter?.setOnItemClickListener { adapter, view, position ->
            val item  = mAdapter?.getItem(position) as VideoBean
            item?.let {
                ARouter.getInstance().build(RouterConfig.PATH_MODULE_COURSE_VIDEOPLAYACTIVITY)//指定跳到那个页面
                    .withString("Url", item.videoUrl)
                    .withString("title", item.title)
                    .navigation()
            }
        }
    }

    override fun onTargetRequestApi(isRefresh: Boolean, page: Int, limit: Int) {
        mPresenter.getVideo()
    }

    override fun createPresenter(): CourseCategoryPresenter {
        return CourseCategoryPresenter()
    }

    override fun showVideo(videoGroup: VideoGroup) {
        setRefreshData(videoGroup.video)
    }


}