package kv.vension.fastframe.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import kv.vension.fastframe.core.AbsCompatActivity
import kv.vension.fastframe.bus.event.BaseEvent
import kv.vension.fastframe.utils.NetworkUtil
import kv.vension.fastframe.utils.PreferenceUtil
import org.greenrobot.eventbus.EventBus

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/10/31 12:23
 * 描  述：网络状态监听
 * ========================================================
 */

class NetworkChangeReceiver : BroadcastReceiver() {

    private val netTAG = "NetworkConnectChanged"
    private var hasNetwork: Boolean by PreferenceUtil("has_network", true)


    companion object{
        private var mReceiver : NetworkChangeReceiver ?= null

        fun getInstance():NetworkChangeReceiver{
            if(mReceiver == null){
                synchronized(NetworkChangeReceiver::class.java){
                    if(mReceiver == null){

                        mReceiver = NetworkChangeReceiver()
                    }
                }
            }
            return mReceiver!!
        }
        fun register(activity : AbsCompatActivity) {
            activity.registerReceiver(getInstance(), IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        }
        fun unRegister(activity : AbsCompatActivity) {
            mReceiver?.let {
                activity.unregisterReceiver(it)
                mReceiver = null
            }
        }
    }


    override fun onReceive(context: Context, intent: Intent) {
        //判断当前的网络连接状态是否可用
        val isConnected = NetworkUtil.isConnected()
        Log.d(netTAG, "onReceive: 当前网络 $isConnected")
        val event = BaseEvent<Any>()
        event.isNetConnected = isConnected
        if (isConnected) {
            if (isConnected != hasNetwork) {
                EventBus.getDefault().post(event)
            }
        } else {
            EventBus.getDefault().post(event)
        }
    }

}