package kv.vension.vframe.ext

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.orhanobut.logger.Logger
import kv.vension.vframe.VFrame
import java.text.SimpleDateFormat
import java.util.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/2 16:15
 * 描  述：拓展类
 * ========================================================
 */

/**
 * Log
 */
fun Loge(tag: String, content: String?) {
    Logger.e(tag,content)
}
fun Loge(content: String) {
    Logger.e(content)
}
fun Logi(content: String) {
    Logger.i(content)
}

fun Any.loge(content: String?) {
    loge(this.javaClass.simpleName, content ?: "")
}

fun loge(tag: String, content: String?) {
    Log.e(tag, content ?: "")
}

fun Fragment.showToast(content: String): Toast {
    val toast = Toast.makeText(this.activity?.applicationContext, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

fun Context.showToast(content: String): Toast {
    val toast = Toast.makeText(VFrame.getContext(), content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}


fun showNotification(context : Context,pendingIntent : PendingIntent,title:String,text:String,sIcon:Int,nId:Int) {
    val systemService = VFrame.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val notificaton = NotificationCompat.Builder(context)
        .setContentTitle(title)
        .setContentText(text)
//        .setStyle(NotificationCompat.BigTextStyle().bigText("我是常温 可就那款了反馈啦你看了那看你看看干那可能那个看看你发你你安康航阿克江卡夫卡巨化股份见客户附近啊控件 将开发机开发框架急啊几口吧"))
        .setDefaults(NotificationCompat.DEFAULT_ALL)
        .setWhen(System.currentTimeMillis())
        .setSmallIcon(sIcon)
//        .setLargeIcon(BitmapFactory.decodeResource(resources,R.mipmap.ic_launcher_round))
        .setContentIntent(pendingIntent)
        .setPriority(NotificationCompat.PRIORITY_MAX)
        .setAutoCancel(true)
        .build()
    systemService.notify(nId,notificaton)
}

/**
 * save cookie string
 */
fun encodeCookie(cookies: List<String>): String {
    val sb = StringBuilder()
    val set = HashSet<String>()
    cookies
        .map { cookie ->
            cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }
        .forEach {
            it.filterNot { set.contains(it) }.forEach { set.add(it) }
        }

    val ite = set.iterator()
    while (ite.hasNext()) {
        val cookie = ite.next()
        sb.append(cookie).append(";")
    }

    val last = sb.lastIndexOf(";")
    if (sb.length - 1 == last) {
        sb.deleteCharAt(last)
    }

    return sb.toString()
}


/**
 * 获取随机rgb颜色值
 */
fun getRandomColor(): Int {
    val random = Random()
    //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
    var red = random.nextInt(190)
    var green = random.nextInt(190)
    var blue = random.nextInt(190)
    //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
    return Color.rgb(red, green, blue)
}


@ColorInt
fun randomColor(@IntRange(from = 0, to = 255) min: Int, @IntRange(from = 0, to = 255) max: Int): Int {
    return Color.rgb(random(min, max), random(min, max), random(min, max))
}
fun random(min: Int, max: Int): Int {
    return Random().nextInt(max - min + 1) + min
}

/**
 * LayoutInflater.from(this).inflate
 * @param resource layoutId
 * @return View
 */
fun Context.inflater(@LayoutRes resource: Int): View =
    LayoutInflater.from(this).inflate(resource, null)

/**
 * String 转 Calendar
 */
fun String.stringToCalendar(): Calendar {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val date = sdf.parse(this)
    val calendar = Calendar.getInstance()
    calendar.time = date
    return calendar
}

/**
 * 格式化当前日期
 */
fun formatCurrentDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    return formatter.format(Date())
}

fun durationFormat(duration: Long?): String {
    val minute = duration!! / 60
    val second = duration % 60
    return if (minute <= 9) {
        if (second <= 9) {
            "0$minute' 0$second''"
        } else {
            "0$minute' $second''"
        }
    } else {
        if (second <= 9) {
            "$minute' 0$second''"
        } else {
            "$minute' $second''"
        }
    }
}

/**
 * 数据流量格式化
 */
fun Context.dataFormat(total: Long): String {
    var result: String
    var speedReal: Int = (total / (1024)).toInt()
    result = if (speedReal < 512) {
        speedReal.toString() + " KB"
    } else {
        val mSpeed = speedReal / 1024.0
        (Math.round(mSpeed * 100) / 100.0).toString() + " MB"
    }
    return result
}

