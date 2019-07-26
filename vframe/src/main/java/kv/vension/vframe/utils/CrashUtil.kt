package kv.vension.vframe.utils

import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import kv.vension.vframe.VFrame
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * ========================================================
 * @author: Created by Vension on 2018/12/19 14:53.
 * @email:  250685***4@qq.com
 * @desc:   崩溃信息收集相关工具类
 * ========================================================
 */
object CrashUtil {

    private var defaultDir: String? = null
    private var dir: String? = null
    private var versionName: String? = null
    private var versionCode: Int = 0

    private var sExecutor: ExecutorService? = null

    private val FILE_SEP = System.getProperty("file.separator")
    private val FORMAT = SimpleDateFormat("MM-dd HH-mm-ss", Locale.getDefault())

    private val CRASH_HEAD: String

    private val DEFAULT_UNCAUGHT_EXCEPTION_HANDLER: Thread.UncaughtExceptionHandler?
    private val UNCAUGHT_EXCEPTION_HANDLER: Thread.UncaughtExceptionHandler


    init {
        try {
            val pi = VFrame.getApplication().packageManager.getPackageInfo(VFrame.getApplication().packageName, 0)
            if (pi != null) {
                versionName = pi.versionName
                versionCode = pi.versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }


        CRASH_HEAD = "\n************* Crash Log Head ****************" +
                "\nDevice Manufacturer: " + Build.MANUFACTURER +// 设备厂商

                "\nDevice Model       : " + Build.MODEL +// 设备型号

                "\nAndroid Version    : " + Build.VERSION.RELEASE +// 系统版本

                "\nAndroid SDK        : " + Build.VERSION.SDK_INT +// SDK版本

                "\nApp VersionName    : " + versionName +
                "\nApp VersionCode    : " + versionCode +
                "\n************* Crash Log Head ****************\n\n"

        DEFAULT_UNCAUGHT_EXCEPTION_HANDLER = Thread.getDefaultUncaughtExceptionHandler()

        UNCAUGHT_EXCEPTION_HANDLER = Thread.UncaughtExceptionHandler { t, e ->
            if (e == null) {
                android.os.Process.killProcess(android.os.Process.myPid())
                System.exit(0)
                return@UncaughtExceptionHandler
            }
            val now = Date(System.currentTimeMillis())
            val fileName = FORMAT.format(now) + "Bug.log"
            val fullPath = (if (dir == null) defaultDir else dir) + fileName
            if (!createOrExistsFile(fullPath)) return@UncaughtExceptionHandler
            if (sExecutor == null) {
                sExecutor = Executors.newSingleThreadExecutor()
            }
            sExecutor?.execute {
                var pw: PrintWriter? = null
                try {
                    pw = PrintWriter(FileWriter(fullPath, false))
                    pw!!.write(CRASH_HEAD)
                    e.printStackTrace(pw)
                    var cause: Throwable? = e.cause
                    while (cause != null) {
                        cause.printStackTrace(pw)
                        cause = cause.cause
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    pw?.close()
                }
            }
            DEFAULT_UNCAUGHT_EXCEPTION_HANDLER?.uncaughtException(t, e)
        }
    }


    /**
     * 初始化
     *
     * 需添加权限 `<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>`
     */
    fun init() {
        init("")
    }

    /**
     * 初始化
     *
     * 需添加权限 `<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>`
     *
     * @param crashDir 崩溃文件存储目录
     */
    fun init(crashDir: File) {
        init(crashDir.absolutePath)
    }


    /**
     * 初始化
     *
     * 需添加权限 `<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>`
     *
     * @param crashDir 崩溃文件存储目录
     */
    fun init(crashDir: String) {
        if (isSpace(crashDir)) {
            dir = null
        } else {
            dir = if (crashDir.endsWith(FILE_SEP.toString())) crashDir else crashDir + FILE_SEP
        }
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() && VFrame.getApplication().externalCacheDir != null)
            defaultDir = VFrame.getApplication().externalCacheDir.toString() + FILE_SEP + "crash" + FILE_SEP
        else {
            defaultDir = VFrame.getApplication().cacheDir.toString() + FILE_SEP + "crash" + FILE_SEP
        }
        Thread.setDefaultUncaughtExceptionHandler(UNCAUGHT_EXCEPTION_HANDLER)
    }


    private fun createOrExistsFile(filePath: String): Boolean {
        val file = File(filePath)
        if (file.exists()) return file.isFile
        if (!createOrExistsDir(file.parentFile)) return false
        try {
            return file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }

    }

    private fun createOrExistsDir(file: File?): Boolean {
        return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
    }

    private fun isSpace(s: String?): Boolean {
        if (s == null) return true
        var i = 0
        val len = s.length
        while (i < len) {
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }



}