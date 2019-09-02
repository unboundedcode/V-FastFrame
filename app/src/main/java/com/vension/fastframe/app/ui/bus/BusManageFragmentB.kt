package com.vension.fastframe.app.ui.bus

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.vension.fastframe.app.R
import com.vension.fastframe.app.ui.bus.event.CommonEvent
import com.vension.fastframe.app.ui.bus.event.StickyEvent
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_bus_manager_b.*
import kv.vension.fastframe.VFrame
import kv.vension.fastframe.core.AbsCompatFragment
import kv.vension.fastframe.ext.Loge
import java.text.SimpleDateFormat
import java.util.*

/**
 * ========================================================================
 * @author: Created by Vension on 2019/8/30 16:15.
 * @email:  vensionHu@qq.com
 * @Github: https://github.com/Vension
 * __      ________ _   _  _____ _____ ____  _   _
 * \ \    / /  ____| \ | |/ ____|_   _/ __ \| \ | |
 *  \ \  / /| |__  |  \| | (___   | || |  | |  \| |
 *   \ \/ / |  __| | . ` |\___ \  | || |  | | . ` |
 *    \  /  | |____| |\  |____) |_| || |__| | |\  |
 *     \/   |______|_| \_|_____/|_____\____/|_| \_|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * @desc: happy code -> 事件总线管理模块
 * ========================================================================
 */
class BusManageFragmentB : AbsCompatFragment() {

    private var isRxBus = false

    override fun initToolBar(mCommonTitleBar: CommonTitleBar, title: String) {
        super.initToolBar(mCommonTitleBar, "事件总线模块B页面")
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_bus_manager_b
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        isRxBus = getBundleExtras().getBoolean("isRxBus")
        btn_post_common.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val createTime = simpleDateFormat.format(Date())
        when(v?.id){
            R.id.btn_post_common->{//发送普通事件
                val bus = if(isRxBus) "RxBus" else "EventBus"
                val event = CommonEvent( "我是来自B页面的${bus}普通事件~FastFrame-Bus->",createTime)
                VFrame.busManager.postEvent(event)
                Handler().postDelayed({
                    mActivity.finish()
                },500)
            }
        }
    }

    @org.greenrobot.eventbus.Subscribe(sticky = true) //如果使用默认的EventBus则使用此@Subscribe
    @com.vension.fastframe.app.ui.bus.rx.Subscribe //如果使用RxBus则使用此@Subscribe
    fun onGetStickyEvent(event: StickyEvent) {
        Loge("onGetStickyEvent----")
        tv_result.text = event.content
    }

    override fun lazyLoadData() {
    }
}
