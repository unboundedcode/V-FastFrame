package com.vension.fastframe.module_course.ui.fragment

import android.os.Bundle
import android.view.View
import com.vension.fastframe.module_course.R
import com.vension.fastframe.module_course.util.SdCardUtils
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_cache_record.*
import kv.vension.fastframe.VFrame
import kv.vension.fastframe.core.AbsCompatFragment

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/25 14:30.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/25 14:30
 * @desc:   缓存中心
 * ===================================================================
 */
class CacheRecordFragment : AbsCompatFragment() {

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_cache_record
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar,title:String) {
        super.initToolBar(mCommonTitleBar,"离线缓存")
        mCommonTitleBar.run {
            setBackgroundColor(VFrame.getColor(R.color.colorCourseMain))
        }
    }
    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        val allSpace = SdCardUtils.getAllSpace()
        val freeSpace = SdCardUtils.getFreeSpace()
        val progress = countProgress(allSpace.replace("GB".toRegex(), "")
            .replace("MB".toRegex(), "")
            .replace("KB".toRegex(), "")
            .toFloat(),
            freeSpace.replace("GB".toRegex(), "")
                .replace("MB".toRegex(), "")
                .replace("KB".toRegex(), "")
                .toFloat())
        progress_bar.progress = progress
        tv_cache_size.text = "主存储:$allSpace/可用:$freeSpace"
    }

    private fun countProgress(allSpace: Float, freeSpace: Float): Int {
        //取整相减
        val size = (Math.floor(allSpace.toDouble()) - Math.floor(freeSpace.toDouble())).toInt()
        val v = size / Math.floor(allSpace.toDouble()) * 100
        return Math.floor(v).toInt()
    }

    override fun lazyLoadData() {
    }
}