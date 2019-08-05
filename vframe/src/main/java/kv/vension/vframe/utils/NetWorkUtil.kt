package kv.vension.vframe.utils

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.telephony.TelephonyManager
import android.util.Log
import kv.vension.vframe.VFrame
import java.io.IOException
import java.net.HttpURLConnection
import java.net.NetworkInterface
import java.net.SocketException
import java.net.URL


object NetWorkUtil {
    private val TAG = NetWorkUtil::class.java.simpleName
    val NET_BROADCAST_ACTION = "com.network.state.action"
    val NET_STATE_NAME = "network_state"

    enum class NetType(var value: Int, var desc: String) {
        /**
         * 当前网络状态
         */
        None(1, "无网络连接"),
        Mobile(2, "移动网络"),
        Wifi(4, "Wifi网络"),
        Other(8, "未知网络")
    }

    /**
     * 接受服务上发过来的广播
     */
    private val mReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent?) {
            if (intent != null) {
                /*
                  -1 为无网络连接
                   1 为WIFI
                   2 为移动网络
                */
                val CURRENT_NETWORK_STATE = intent.getIntExtra(NET_STATE_NAME, -1)
                when (CURRENT_NETWORK_STATE) {
                    -1 -> Log.i(TAG, "网络更改为 无网络  CURRENT_NETWORK_STATE =$CURRENT_NETWORK_STATE")
                    1 -> Log.i(TAG, "网络更改为 WIFI网络  CURRENT_NETWORK_STATE=$CURRENT_NETWORK_STATE")
                    2 -> Log.i(TAG, "网络更改为 移动网络  CURRENT_NETWORK_STATE =$CURRENT_NETWORK_STATE")
                }
            }
        }
    }

    /**
     * 获取TelephonyManager
     */
    fun getTelephonyManager(context: Context): TelephonyManager {
        return context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }

    /**
     * 获取ConnectivityManager
     */
    private fun getConnectivityManager(context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    /**
     * 判断网络是否连接
     *
     * @return
     */
    fun isConnected(): Boolean {
        val manager = VFrame.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = manager.activeNetworkInfo
        return info != null && info.isConnected
    }


    /**
     * 判断当前网络是否正在连接中或则已连接
     */
    fun isConnectedOrConnecting(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val nets = getConnectivityManager(context).allNetworks
            nets?.let {
                for (network in nets) {
                    val networkInfo = getConnectivityManager(context).getNetworkInfo(network)
                    if (networkInfo.isConnected) {
                        return true
                    }
                }
            }
        } else {
            val nets = getConnectivityManager(context).allNetworkInfo
            nets?.let {
                for (networkInfo in nets) {
                    if (networkInfo.isConnectedOrConnecting) {
                        return true
                    }
                }
            }
        }
        return false
    }

    /**
     * 获取当前网络类型（移动网络还是Wifi）
     */
    fun getConnectedType(context: Context): NetType {
        val NET = getConnectivityManager(context).activeNetworkInfo
        if (NET != null) {
            when (NET.subtype) {
                ConnectivityManager.TYPE_WIFI -> return NetType.Wifi
                ConnectivityManager.TYPE_MOBILE -> return NetType.Mobile
                else -> return NetType.Other
            }
        }
        return NetType.None
    }


    /**
     * check NetworkAvailable
     *
     * @return
     */
    @JvmStatic
    fun isNetworkAvailable(): Boolean {
        val manager = VFrame.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = manager.activeNetworkInfo
        return !(null == info || !info.isAvailable)
    }


    /**
     * 获取当前是否存在有效的Wifi连接
     */
    fun isWifiConnected(context: Context): Boolean {
        val networkInfo = getConnectivityManager(context).activeNetworkInfo
        return networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_WIFI && networkInfo.isConnected
    }

    /**
     * 获取当前是否存在有效的网络连接
     */
    fun isMobileConnected(context: Context): Boolean {
        val networkInfo = getConnectivityManager(context).activeNetworkInfo
        return networkInfo != null && networkInfo.type == ConnectivityManager.TYPE_MOBILE && networkInfo.isConnected
    }

    /**
     * 判断MOBILE网络是否可用
     */
    fun isMobile(): Boolean {
        //获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
        val manager = VFrame.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //获取NetworkInfo对象
        val networkInfo = manager.activeNetworkInfo
        //判断NetworkInfo对象是否为空 并且类型是否为MOBILE
        if (null != networkInfo && networkInfo.type == ConnectivityManager.TYPE_MOBILE)
            return networkInfo.isAvailable
        return false
    }

    /**
     * is wifi on
     */
    @JvmStatic
    fun isWifiEnabled(): Boolean {
        val mgrConn = VFrame.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mgrTel = VFrame.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return mgrConn.activeNetworkInfo != null && mgrConn
            .activeNetworkInfo.state == NetworkInfo.State.CONNECTED || mgrTel
            .networkType == TelephonyManager.NETWORK_TYPE_UMTS
    }


    /**
     * 判断是否是wifi连接
     *
     * @return boolean
     */
    fun isWifi(): Boolean {
        val connectivityManager = VFrame.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_WIFI
    }


    /**
     * check is3G
     *
     * @return boolean
     */
    @JvmStatic
    fun is3G(): Boolean {
        val connectivityManager = VFrame.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_MOBILE
    }

    /**
     * is2G
     *
     * @return boolean
     */
    @JvmStatic
    fun is2G(): Boolean {
        val connectivityManager = VFrame.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return activeNetInfo != null && (activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_EDGE
                || activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_GPRS || activeNetInfo
            .subtype == TelephonyManager.NETWORK_TYPE_CDMA)
    }

    /**
     * 打开网络设置界面
     */
    fun openSetting(activity: Activity) {
        val intent = Intent("/")
        val cm = ComponentName(
            "com.android.settings",
            "com.android.settings.WirelessSettings"
        )
        intent.component = cm
        intent.action = "android.intent.action.VIEW"
        activity.startActivityForResult(intent, 0)
    }


    /**
     * 得到ip地址
     *
     * @return
     */
    @JvmStatic
    fun getLocalIpAddress(): String {
        var ret = ""
        try {
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val enumIpAddress = en.nextElement().inetAddresses
                while (enumIpAddress.hasMoreElements()) {
                    val netAddress = enumIpAddress.nextElement()
                    if (!netAddress.isLoopbackAddress) {
                        ret = netAddress.hostAddress.toString()
                    }
                }
            }
        } catch (ex: SocketException) {
            ex.printStackTrace()
        }

        return ret
    }


    /**
     * ping "http://www.baidu.com"
     *
     * @return
     */
    private val TIMEOUT = 3000 // TIMEOUT
    @JvmStatic
    private fun pingNetWork(): Boolean {
        var result = false
        var httpUrl: HttpURLConnection? = null
        try {
            httpUrl = URL("http://www.baidu.com")
                .openConnection() as HttpURLConnection
            httpUrl.connectTimeout = TIMEOUT
            httpUrl.connect()
            result = true
        } catch (e: IOException) {
        } finally {
            if (null != httpUrl) {
                httpUrl.disconnect()
            }
        }
        return result
    }

}