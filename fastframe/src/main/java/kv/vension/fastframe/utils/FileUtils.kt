package kv.vension.fastframe.utils

import android.content.Context
import android.content.res.AssetManager
import android.os.Environment
import android.util.Log
import java.io.*

/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/26 15:49
 * 更 新：2019/7/26 15:49
 *File文件工具类
 * ========================================================
 */

object FileUtils {

    /**
     * 判断是否有SD卡
     */
    val isSdCardAvailable = Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()

    /**
     * 创建根缓存目录
     */
    fun createRootPath(context: Context): String {
        val cacheRootPath: String
        if (isSdCardAvailable) {
            // /sdcard/Android/data/<application package>/cache
            cacheRootPath = context.externalCacheDir!!.path
        } else {
            // /data/data/<application package>/cache
            cacheRootPath = context.cacheDir.path
        }
        return cacheRootPath
    }

    /**
     * 递归创建文件夹
     */
    fun createDir(dirPath: String): String {
        try {
            val file = File(dirPath)
            if (file.parentFile.exists()) {
                Log.i("----- 创建文件夹",  file.absolutePath.toString())
                file.mkdir()
                return file.absolutePath
            } else {
                createDir(file.parentFile.absolutePath)
                Log.i("----- 创建文件夹",  file.absolutePath.toString())
                file.mkdir()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dirPath
    }

    /**
     * 递归创建文件夹
     */
    fun createFile(file: File): String {
        try {
            if (file.parentFile.exists()) {
                Log.i("----- 创建文件夹",  file.absolutePath.toString())
                file.createNewFile()
                return file.absolutePath
            } else {
                createDir(file.parentFile.absolutePath)
                file.createNewFile()
                Log.i("----- 创建文件夹",  file.absolutePath.toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 写入文件流
     */
    fun writeFile(filePathAndName: String, fileContent: String) {
        try {
            val outStream = FileOutputStream(filePathAndName)
            val writerStream = OutputStreamWriter(outStream)
            writerStream.write(fileContent)
            writerStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 文件拷贝
     */
    fun fileChannelCopy(src: File, desc: File) {
        var fi: FileInputStream? = null
        var fo: FileOutputStream? = null
        try {
            fi = FileInputStream(src)
            fo = FileOutputStream(desc)
            val `in` = fi.channel//得到对应的文件通道
            val out = fo.channel//得到对应的文件通道
            `in`.transferTo(0, `in`.size(), out)//连接两个通道，并且从in通道读取，然后写入out通道
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                fo?.close()
                fi?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 打开Asset下的文件
     */
    fun openAssetFile(context: Context, fileName: String): InputStream? {
        val am: AssetManager = context.assets
        var `is`: InputStream? = null
        try {
            `is` = am.open(fileName)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return `is`
    }

}
