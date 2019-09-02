package com.vension.fastframe.app.ui.bus

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.vension.fastframe.app.R
import com.vension.fastframe.app.ui.bus.event.CommonEvent
import com.vension.fastframe.app.ui.bus.event.StickyEvent
import com.vension.fastframe.app.ui.bus.rx.RxBusManager
import kotlinx.android.synthetic.main.fragment_bus_manager_a.*
import kv.vension.fastframe.VFrame
import kv.vension.fastframe.bus.EventBusManager
import kv.vension.fastframe.core.AbsCompatFragment
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
class BusManageFragmentA : AbsCompatFragment() {

    private var isRxBus = false

    companion object{
        fun newInstance() : BusManageFragmentA {
            return BusManageFragmentA()
        }
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_bus_manager_a
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        btn_switch_bus.setOnClickListener(this)
        btn_post_sticky.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val createTime = simpleDateFormat.format(Date())
        when(v?.id){
            R.id.btn_switch_bus->{//切换方式最好在Application初始化时设置
                VFrame.busManager.unRegister(this)//切换方式时先注销
                isRxBus = !isRxBus
                VFrame.busManager = if(isRxBus) RxBusManager() else EventBusManager()
                btn_switch_bus.text = if(isRxBus) "切换EventBus" else "切换RxBus"
                tv_result.text = ""
                VFrame.busManager.register(this)//切换方式时需要重新注册
            }
            R.id.btn_post_sticky->{//发送粘性事件
                val bus = if(isRxBus) "RxBus" else "EventBus"
                val event = StickyEvent("我是来自A页面的${bus}粘性事件~FastFrame-Bus->",createTime)
                VFrame.busManager.postStickyEvent(event)
                Handler().postDelayed({
                    val bundle = Bundle()
                    bundle.putBoolean("isRxBus",isRxBus)
                    startProxyActivity(BusManageFragmentB::class.java,bundle)
                },500)
            }
        }
    }


    @org.greenrobot.eventbus.Subscribe //如果使用默认的EventBus则使用此@Subscribe
    @com.vension.fastframe.app.ui.bus.rx.Subscribe//如果使用RxBus则使用此@Subscribe
    fun onGetEvent(event: CommonEvent) {
        tv_result.text = event.content
    }

    override fun lazyLoadData() {
    }

}