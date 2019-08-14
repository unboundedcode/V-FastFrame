package kv.vension.fastframe.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import kv.vension.fastframe.VFrame
import java.io.*


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/21 10:14
 * 描  述：SharedPreferences 工具类
 *        commit方法属于属于阻塞性质API，建议使用apply。
 * ========================================================
 */

object SPUtil {

    private val sp_name = "SP.cache"

    val sp: SharedPreferences
        get() = VFrame.getApplication().getSharedPreferences(sp_name, Context.MODE_PRIVATE)


    /**
     * SP中写入key类型value
     *
     * @param key   键
     * @param value 值
     */
    fun <T> put(key: String?, value: T){
        when (value) {
            is Long -> sp.edit().putLong(key, value)
            is String -> sp.edit().putString(key, value)
            is Int -> sp.edit().putInt(key, value)
            is Boolean -> sp.edit().putBoolean(key, value)
            is Float -> sp.edit().putFloat(key, value)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }.apply()
    }

    /**
     * SP中读取value
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code null}
     */
    fun <T> get(key: String,default: T): T {
        val res: Any = when (default) {
            is Long -> sp.getLong(key, default)
            is String -> sp.getString(key, default)
            is Int -> sp.getInt(key, default)
            is Boolean -> sp.getBoolean(key, default)
            is Float -> sp.getFloat(key, default)
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
        val objectOutputStream = ObjectOutputStream(
            byteArrayOutputStream)
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
            redStr.toByteArray(charset("ISO-8859-1")))
        val objectInputStream = ObjectInputStream(
            byteArrayInputStream)
        val obj = objectInputStream.readObject() as A
        objectInputStream.close()
        byteArrayInputStream.close()
        return obj
    }

    fun put(key: String?, value: String) {
        sp.edit().putString(key, value).apply()
    }

    /**
     * SP中读取String
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code null}
     */
    fun getString(key: String): String? {
        return getString(key, "")
    }


    /**
     * SP中读取String
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    fun getString(key: String, defaultValue: String): String? {
        return sp.getString(key, defaultValue)
    }

    /**
     * SP中写入int类型value
     *
     * @param key   键
     * @param value 值
     */
    fun put(key: String?, value: Int) {
        sp.edit().putInt(key, value).apply()
    }


    /**
     * SP中读取int
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    fun getInt(key: String?): Int {
        return getInt(key,0)
    }

    /**
     * SP中读取int
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    fun getInt(key: String?, defaultValue: Int): Int {
        return sp.getInt(key, defaultValue)
    }


    /**
     * SP中写入long类型value
     *
     * @param key   键
     * @param value 值
     */
    fun put(key: String?, value: Long) {
        sp.edit().putLong(key, value).apply()
    }

    /**
     * SP中读取long
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    fun getLong(key: String?): Long {
        return getLong(key, -1L)
    }

    /**
     * SP中读取long
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    fun getLong(key: String?, defaultValue: Long): Long {
        return sp.getLong(key, defaultValue)
    }


    /**
     * SP中写入float类型value
     *
     * @param key   键
     * @param value 值
     */
    fun put(key: String?, value: Float) {
        sp.edit().putFloat(key, value).apply()
    }


    /**
     * SP中读取float
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    fun getFloat(key: String?): Float {
        return getFloat(key, -1f)
    }


    /**
     * SP中读取float
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    fun getFloat(key: String?, defaultValue: Float): Float {
        return sp.getFloat(key, defaultValue)
    }


    /**
     * SP中写入boolean类型value
     *
     * @param key   键
     * @param value 值
     */
    fun put(key: String?, value: Boolean) {
        sp.edit().putBoolean(key, value)
    }


    /**
     * SP中读取boolean
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code false}
     */
    fun getBoolean(key: String?): Boolean {
        return getBoolean(key, false)
    }


    /**
     * SP中读取boolean
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
        return sp.getBoolean(key, defaultValue)
    }

    /**
     * SP中写入String集合类型value
     *
     * @param key    键
     * @param values 值
     */
    fun put(key: String?, value: Set<String>) {
        sp.edit().putStringSet(key, value).apply()
    }


    /**
     * SP中读取StringSet
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code null}
     */
    fun getStringSet(key: String?): Set<String> {
        return getStringSet(key, null)
    }


    /**
     * SP中读取StringSet
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    fun getStringSet(key: String?, defaultValue: Set<String>?): Set<String> {
        return sp.getStringSet(key, defaultValue)!!
    }

    /**
     * SP中获取所有键值对
     *
     * @return Map对象
     */
    fun getAll(): Map<String, *> {
        return sp.all
    }

    /**
     * SP中移除该key
     *
     * @param key 键
     */
    fun remove(key: String?) {
        sp.edit().remove(key).apply()
    }


    /**
     * SP中是否存在该key
     *
     * @param key 键
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    fun contains(key: String?): Boolean {
        return sp.contains(key)
    }


    /**
     * SP中清除所有数据
     */
    fun clear() {
        sp.edit().clear().apply()
    }


    /**
     * desc:保存对象
     * @param obj 要保存的对象，只能保存实现了serializable的对象
     * modified:
     */
    fun saveObject(key: String?, obj: Any) {
        val str = Gson().toJson(obj, obj.javaClass)
        sp.edit().putString(key, str)
        sp.edit().commit()
    }


    fun getObject(key: String?, clazz: Class<*>): Any? {
        val str = sp.getString(key, "")
        if (str != null) {
            return Gson().fromJson(str, clazz)
        }
        return null
    }

}