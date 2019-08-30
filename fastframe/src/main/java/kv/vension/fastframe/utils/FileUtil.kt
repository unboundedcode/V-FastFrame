package kv.vension.fastframe.utils

import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import androidx.core.content.FileProvider
import java.io.*
import java.util.*

/**
 * ========================================================================
 * 作 者：Vension
 * 主 页：Github: https://github.com/Vension
 * 日 期：2019/8/23 16:35
 *   ______       _____ _______ /\/|_____ _____            __  __ ______
 *  |  ____/\    / ____|__   __|/\/  ____|  __ \     /\   |  \/  |  ____|
 *  | |__ /  \  | (___    | |     | |__  | |__) |   /  \  | \  / | |__
 *  |  __/ /\ \  \___ \   | |     |  __| |  _  /   / /\ \ | |\/| |  __|
 *  | | / ____ \ ____) |  | |     | |    | | \ \  / ____ \| |  | | |____
 *  |_|/_/    \_\_____/   |_|     |_|    |_|  \_\/_/    \_\_|  |_|______|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * 描述：File文件工具类
 * ========================================================================
 */
object FileUtil {

    private val TAG = FileUtil::class.java.simpleName

    /**
     * 判断是否有SD卡,SD卡是否能用
     *
     * @return true 可用,false不可用
     */
    val isSdCardAvailable = Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()

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
     * 创建未存在的文件夹
     *
     * @param file
     * @return
     */
    fun makeDirs(file: File): File {
        if (!file.exists()) {
            file.mkdirs()
        }
        return file
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
     * 创建一个文件夹, 存在则返回, 不存在则新建
     *
     * @param parentDirectory 父目录路径
     * @param directory       目录名
     * @return 文件，null代表失败
     */
    fun getDirectory(parentDirectory: String, directory: String): File? {
        if (TextUtils.isEmpty(parentDirectory) || TextUtils.isEmpty(directory)) {
            return null
        }
        val file = File(parentDirectory, directory)
        val flag: Boolean
        if (!file.exists()) {
            flag = file.mkdir()
        } else {
            flag = true
        }
        return if (flag) file else null
    }

    /**
     * 创建一个文件夹, 存在则返回, 不存在则新建
     *
     * @param parentDirectory 父目录
     * @param directory       目录名
     * @return 文件，null代表失败
     */
    fun getDirectory(parentDirectory: File?, directory: String): File? {
        if (parentDirectory == null || TextUtils.isEmpty(directory)) {
            return null
        }
        val file = File(parentDirectory, directory)
        val flag: Boolean
        if (!file.exists()) {
            flag = file.mkdir()
        } else {
            flag = true
        }
        return if (flag) file else null
    }


    /**
     * 创建一个文件, 存在则返回, 不存在则新建
     *
     * @param catalogPath 父目录路径
     * @param name        文件名
     * @return 文件，null代表失败
     */
    fun getFile(catalogPath: String, name: String): File? {
        if (TextUtils.isEmpty(catalogPath) || TextUtils.isEmpty(name)) {
            Log.e(TAG, "getFile : 创建失败, 文件目录或文件名为空, 请检查!")
            return null
        }
        var flag: Boolean
        val file = File(catalogPath, name)
        if (!file.exists()) {
            try {
                flag = file.createNewFile()
            } catch (e: IOException) {
                Log.e(TAG, "getFile : 创建" + catalogPath + "目录下的文件" + name + "文件失败!", e)
                flag = false
            }

        } else {
            flag = true
        }
        return if (flag) file else null
    }


    /**
     * 创建一个文件, 存在则返回, 不存在则新建
     *
     * @param catalog 父目录
     * @param name    文件名
     * @return 文件，null代表失败
     */
    fun getFile(catalog: File?, name: String): File? {
        if (catalog == null || TextUtils.isEmpty(name)) {
            Log.e(TAG, "getFile : 创建失败, 文件目录或文件名为空, 请检查!")
            return null
        }
        var flag: Boolean
        val file = File(catalog, name)
        if (!file.exists()) {
            try {
                flag = file.createNewFile()
            } catch (e: IOException) {
                Log.e(TAG, "getFile : 创建" + catalog + "目录下的文件" + name + "文件失败!", e)
                flag = false
            }

        } else {
            flag = true
        }
        return if (flag) file else null
    }

    /**
     * 获取指定文件夹大小
     */
     fun getFileSize(file: File) : Long {
        var size = 0L
        if (file == null || !file.isDirectory || !file.exists()) {
            return 0L
        }
        try {
            val fileList = file.listFiles() ?: return 0L
            for (file in fileList) {
                size += if (file.isDirectory) getFileSize(file) else file.length()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }


    /**
     * 转化为文件的大小单位
     */
    fun formatFileSize(size: Long): String {
        return when {
            size < 0 -> throw IllegalArgumentException("File size cant less than 0!")
            size < 1024 -> String.format(Locale.getDefault(), "%.3fB", size.toDouble())
            size < 1048576 -> String.format(Locale.getDefault(), "%.3fKB", size.toDouble() / 1024)
            size < 1073741824 -> String.format(Locale.getDefault(), "%.3fMB", size.toDouble() / 1048576)
            else -> String.format(Locale.getDefault(), "%.3fGB", size.toDouble() / 1073741824)
        }
    }

    /**
     * 根据全路径创建一个文件
     *
     * @param filePath 文件全路径
     * @return 文件，null代表失败
     */
    fun getFile(filePath: String): File? {
        if (TextUtils.isEmpty(filePath)) {
            Log.e(TAG, "getFile : 创建失败, 文件目录或文件名为空, 请检查!")
            return null
        }
        var flag: Boolean
        val file = File(filePath)
        if (!file.exists()) {
            try {
                flag = file.createNewFile()
            } catch (e: IOException) {
                Log.e(TAG, "getFile : 创建" + file.name + "文件失败!", e)
                flag = false
            }

        } else {
            flag = true
        }
        return if (flag) file else null
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
     * 计算文件/文件夹的大小
     *
     * @param file 文件或文件夹
     * @return 文件大小
     */
    fun calculateFileSize(file: File?): Long {
        if (file == null) {
            return 0
        }

        if (!file.exists()) {
            return 0
        }

        var result: Long = 0
        if (file.isDirectory) {
            val files = file.listFiles()
            if (null != files) {
                for (subFile in files) {
                    if (subFile.isDirectory) {
                        result += calculateFileSize(subFile)
                    } else {
                        result += subFile.length()
                    }
                }
            }
        }
        result += file.length()
        return result
    }

    /**
     * 删除文件夹中的所有文件
     *
     * @param file 指定的文件夹
     * @param isDeleteSelf 是否删除文件夹本身
     * @return true代表成功删除
     */
    fun deleteFile(file: File?, isDeleteSelf: Boolean): Boolean {
        if (file == null) {
            return true
        }
        if (!file.exists()) {
            return true
        }
        var result = true
        if (file.isDirectory) {
            val files = file.listFiles()
            if (null != files) {
                for (subFile in files) {
                    if (subFile.isDirectory) {
                        if (!deleteFile(subFile, true)) {
                            result = false
                        }
                    } else {
                        if (!subFile.delete()) {
                            result = false
                        }
                    }
                }
            }
        }

        if (isDeleteSelf) {
            if (!file.delete()) {
                result = false
            }
        }

        return result
    }


    /**
     * 文件拷贝
     */
    fun copyFile(source: File, target: File): Boolean {
        val `in` = FileInputStream(source)
        val out = FileOutputStream(target)
        try {
            val bytes = ByteArray(1024)
            while (`in`.read(bytes) != -1) {
                out.write(bytes, 0, bytes.size)
//                outputStream.write(bytes, 0, read)
            }
            out.flush()
            return true
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return false
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        } finally {
            out.close()
            `in`.close()
        }
    }

    fun saveFile(inputStream: InputStream?, outputStream: OutputStream?): Boolean {
        if (inputStream == null || outputStream == null) {
            return false
        }

        try {
            try {
                val buffer = ByteArray(1024 * 4)

                while (true) {
                    val read = inputStream.read(buffer)
                    if (read == -1) {
                        break
                    }
                    outputStream.write(buffer, 0, read)
                }
                outputStream.flush()
                return true
            } catch (e: IOException) {
                e.printStackTrace()
                return false
            } finally {
                inputStream.close()
                outputStream.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }

    }


    /**
     * （需要外部存储权限）
     * @param type 所放的文件的类型，传入的参数是Environment类中的DIRECTORY_XXX静态变量
     * @return 返回"/storage/emulated/0/xxx"目录
     * 例如传入Environment.DIRECTORY_ALARMS则返回"/storage/emulated/0/Alarms"
     */
    fun getExternalStoragePublicDirectory(type: String): String {
        val file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS)
        //返回的目录有可能不存在
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.absolutePath
    }

    //返回"/data"目录
    fun getDataDirectory(): String {
        return Environment.getDataDirectory().absolutePath
    }

    //返回"/storage/emulated/0"目录（需要外部存储权限）
    fun getExternalStorageDirectory(): String {
        return Environment.getExternalStorageDirectory().absolutePath
    }

    //返回"/system"目录
    fun getRootDirectory(): String {
        return Environment.getRootDirectory().absolutePath
    }

    //返回"/cache"目录
    fun getDownloadCacheDirectory(): String {
        return Environment.getDownloadCacheDirectory().absolutePath
    }

    //返回"/data/user/0/com.xxx.xxx/cache"目录
    fun getCacheDir(context: Context): String {
        return context.cacheDir.absolutePath
    }

    //返回"/data/user/0/com.xxx.xxx/files"目录
    fun getFilesDir(context: Context): String {
        return context.filesDir.absolutePath
    }

    //返回"/storage/emulated/0/Android/data/com.xxx.xxx/cache"目录
    fun getExternalCacheDir(context: Context): String {
        return context.externalCacheDir!!.absolutePath
    }


    /**
     * @param type 所放的文件的类型，传入的参数是Environment类中的DIRECTORY_XXX静态变量
     * @return 返回"/storage/emulated/0/Android/data/com.xxx.xxx/files/Alarms"目录
     * 例如传入Environment.DIRECTORY_ALARMS则返回"/storage/emulated/0/Android/data/com.xxx.xxx/files/Alarms"
     */
    fun getExternalFilesDir(context: Context, type: String): String {
        val file = context.getExternalFilesDir(Environment.DIRECTORY_ALARMS)
        //返回的目录有可能不存在
        if (!file!!.exists()) {
            file.mkdirs()
        }
        return file.absolutePath
    }

    /** android 7.0 File适配相关 开始 **/

    /**
     * 根据file获取uri，适配7.0系统
     */
    fun getUriForFile(context: Context, file: File): Uri {
        val fileUri: Uri
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = getUriForFileAndroid7(context, file)
        } else {
            fileUri = Uri.fromFile(file)
        }
        return fileUri
    }

    fun getUriForFileAndroid7(context: Context, file: File): Uri {
        return FileProvider.getUriForFile(context, context.packageName + ".android7.fileprovider", file)
    }

    /**
     * 授予文件的读写权限
     * @param context 上下文
     * @param packageName 应用包名
     * @param fileUri 文件uri
     */
    fun grantUriPermission(context: Context, packageName: String, fileUri: Uri) {
        context.grantUriPermission(packageName, fileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    }

    fun setIntentDataAndType(context: Context, intent: Intent, file: File, type: String, writeAble: Boolean) {
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setDataAndType(getUriForFile(context, file), type)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            }
        } else {
            intent.setDataAndType(Uri.fromFile(file), type)
        }
    }
    /** android 7.0 File适配相关 结束 **/


}
