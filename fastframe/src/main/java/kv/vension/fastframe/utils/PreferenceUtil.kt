package kv.vension.fastframe.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import kv.vension.fastframe.VFrame
import java.io.*
import kotlin.reflect.KProperty

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/10/31 10:06
 * 描  述：kotlin委托属性+SharedPreference实例
 *        commit方法属于属于阻塞性质API，建议使用apply。
 * ========================================================
 */
class PreferenceUtil<T>(val name: String, private val default: T) {


    companion object {
        private const val fileName = "SP.cache"

        private val mSharedPreferences: SharedPreferences by lazy {
            VFrame.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE)
        }

        /**
         * 查询某个key是否已经存在
         *
         * @param key
         * @return
         */
        fun contains(key: String): Boolean {
            return mSharedPreferences.contains(key)
        }

        /**
         * 返回所有的键值对
         *
         * @param context
         * @return
         */
        fun getAll(): Map<String, *> {
            return mSharedPreferences.all
        }

        /**
         * 根据key删除存储数据
         */
        fun remove(key: String) {
            mSharedPreferences.edit().remove(key).apply()
        }

        /**
         * 删除全部数据
         */
        fun clear() {
            mSharedPreferences.edit().clear().apply()
        }


    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = getSharedPreferences(name, default)

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = putSharedPreferences(name, value)

    @SuppressLint("CommitPrefEdits")
    private fun <T> putSharedPreferences(key: String, value: T) = with(mSharedPreferences.edit()) {
        when (value) {
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            else -> putString(key, serialize(value))
        }.apply()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> getSharedPreferences(key: String, default: T): T = with(mSharedPreferences) {
        val res: Any = when (default) {
            is Long -> getLong(key, default)
            is String -> getString(key, default)
            is Int -> getInt(key, default)
            is Boolean -> getBoolean(key, default)
            is Float -> getFloat(key, default)
            else -> deSerialization(getString(key, serialize(default)).toString())
        }!!
        return res as T
    }



    /**
     * 序列化对象

     * @param person
     * *
     * @return
     * *
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun <A> serialize(obj: A): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(obj)
        var serStr = byteArrayOutputStream.toString("ISO-8859-1")
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()
        return serStr
    }

    /**
     * 反序列化对象

     * @param str
     * *
     * @return
     * *
     * @throws IOException
     * *
     * @throws ClassNotFoundException
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IOException::class, ClassNotFoundException::class)
    private fun <A> deSerialization(str: String): A {
        val redStr = java.net.URLDecoder.decode(str, "UTF-8")
        val byteArrayInputStream = ByteArrayInputStream(
            redStr.toByteArray(charset("ISO-8859-1"))
        )
        val objectInputStream = ObjectInputStream(
            byteArrayInputStream
        )
        val obj = objectInputStream.readObject() as A
        objectInputStream.close()
        byteArrayInputStream.close()
        return obj
    }

}
