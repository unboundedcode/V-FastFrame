package kv.vension.fastframe.cache

import android.content.Context
import android.os.Environment
import kv.vension.fastframe.utils.FileUtil.getFileSize
import java.io.File
import java.text.DecimalFormat

/**
 * ========================================================
 * @author: Created by Vension on 2018/12/4 15:05.
 * @email:  250685***4@qq.com
 * @desc:   缓存路径处理类
 * ========================================================
 */
object FileCache {

    private const val F_IMAGE = "f_image"
    private const val F_VIDEO = "f_video"
    private const val F_AUDIO = "f_audio"
    private const val F_FILE = "f_file"

    private var vPath: String? = null
    private var vCacheImage: File? = null
    private var vCacheVideo: File? = null
    private var vCacheAudio: File? = null
    private var vCacheFile: File? = null

    private const val TYPE_B = 1// 获取文件大小单位为B的double值
    private const val TYPE_KB = 2// 获取文件大小单位为KB的double值
    private const val TYPE_MB = 3// 获取文件大小单位为MB的double值
    private const val TYPE_GB = 4// 获取文件大小单位为GB的double值

    fun init(context: Context) {
        vPath = getLocatDir(context) + File.separator

        if (vCacheImage == null) {
            vCacheImage = File(vPath!! + F_IMAGE)
            if (!vCacheImage!!.mkdir()) {
                vCacheImage!!.mkdirs()
            }
        }

        if (vCacheVideo == null) {
            vCacheVideo = File(vPath!! + F_VIDEO)
            if (!vCacheVideo!!.mkdir()) {
                vCacheVideo!!.mkdirs()
            }
        }

        if (vCacheAudio == null) {
            vCacheAudio = File(vPath!! + F_AUDIO)
            if (!vCacheAudio!!.mkdir()) {
                vCacheAudio!!.mkdirs()
            }
        }

        if (vCacheFile == null) {
            vCacheFile = File(vPath!! + F_FILE)
            if (!vCacheFile!!.mkdir()) {
                vCacheFile!!.mkdirs()
            }
        }
    }


    fun isSdcardExist(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    fun getCachePath(): String? {
        return vPath
    }

    fun getCacheImagePath(): String {
        return getPath(vCacheImage)
    }

    fun getCacheVideoPath(): String {
        return getPath(vCacheVideo)
    }

    fun getCacheAudioPath(): String {
        return getPath(vCacheAudio)
    }

    fun getCacheFilePath(): String {
        return getPath(vCacheFile)
    }


    private fun getPath(file: File?): String {
        if (file != null) {
            if (!file.exists() && !file.mkdir()) {
                file.mkdirs()
            }
            return file.absolutePath
        }
        return "/Android/data/com.r747223875.nfu/cache/errCache/"
    }


    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     */
    fun getFileOrFilesSize(filePath: String, sizeType: Int): Double {
        val file = File(filePath)
        var blockSize: Long = 0
        try {
            if (file.isDirectory) {
                blockSize = getFileSizes(file)
            } else {
                blockSize = getFileSize(file)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return formetFileSize(blockSize, sizeType)
    }

    /**
     * 自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件或指定文件夹路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    fun getAutoFileOrFilesSize(filePath: String): String {
        val file = File(filePath)
        var blockSize: Long = 0
        try {
            if (file.isDirectory) {
                blockSize = getFileSizes(file)
            } else {
                blockSize = getFileSize(file)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return formetFileSize(blockSize)
    }

    // 文件目录下文件随APK卸掉而移除
    fun getLocatDir(context: Context): String {
        var mCacheFile: File? = null

        try {
            if (isSdcardExist()) {
                mCacheFile = context.externalCacheDir
            }

            if (mCacheFile == null) {
                mCacheFile = context.cacheDir
            }
        } catch (e: Exception) {
            mCacheFile = context.filesDir
        }

        if (!mCacheFile!!.exists()) {
            mCacheFile.mkdirs()
        }
        return mCacheFile.absolutePath
    }

    /**
     * 获取指定文件夹大小
     */
    @Throws(Exception::class)
    fun getFileSizes(file: File?): Long {
        if (file == null || !file.isDirectory || !file.exists()) {
            return 0L
        }
        var size: Long = 0
        val flist = file.listFiles() ?: return 0L
        for (i in flist.indices) {
            if (flist[i].isDirectory) {
                size += getFileSizes(flist[i])
            } else {
                size += getFileSize(flist[i])
            }
        }
        return size
    }

    /**
     * 转换文件大小
     */
    fun formetFileSize(fileS: Long): String {
        val df = DecimalFormat("#.00")
        var fileSizeString :String
        val wrongSize = "0B"
        if (fileS == 0L) {
            return wrongSize
        }
        if (fileS < 1024) {
            fileSizeString = df.format(fileS.toDouble()) + "B"
        } else if (fileS < 1048576) {
            fileSizeString = df.format(fileS.toDouble() / 1024) + "KB"
        } else if (fileS < 1073741824) {
            fileSizeString = df.format(fileS.toDouble() / 1048576) + "MB"
        } else {
            fileSizeString = df.format(fileS.toDouble() / 1073741824) + "GB"
        }
        return fileSizeString
    }

    /**
     * 转换文件大小,指定转换的类型
     */
    fun formetFileSize(fileS: Long, sizeType: Int): Double {
        val df = DecimalFormat("#.00")
        var fileSizeLong = 0.0
        when (sizeType) {
            TYPE_B -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble()))
            TYPE_KB -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble() / 1024))
            TYPE_MB -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble() / 1048576))
            TYPE_GB -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble() / 1073741824))
        }
        return fileSizeLong
    }


    // 按目录删除文件夹文件方法
    private fun deleteFolderFile(filePath: String, deleteThisPath: Boolean): Boolean {
        try {
            val file = File(filePath)
            if (file.isDirectory) {
                val files = file.listFiles()
                for (file1 in files!!) {
                    deleteFolderFile(file1.absolutePath, true)
                }
            }
            if (deleteThisPath) {
                if (!file.isDirectory) {
                    file.delete()
                } else {
                    if (file.listFiles()!!.isEmpty()) {
                        file.delete()
                    }
                }
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    fun clear(file: File) {
        if (file.isFile) {
            file.delete()
            return
        }

        if (file.isDirectory) {
            val childFiles = file.listFiles()
            if (childFiles == null || childFiles.isEmpty()) {
                return
            }

            for (i in childFiles.indices) {
                clear(childFiles[i])
            }
        }
    }

}