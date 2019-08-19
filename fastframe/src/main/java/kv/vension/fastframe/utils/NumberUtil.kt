package kv.vension.fastframe.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/1 16:25
 * 描  述：数字转换工具
 * 进行BigDecimal对象的加减乘除，四舍五入等运算的工具类<br></br>
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精 确的浮点数运算，包括加减乘除和四舍五入。
 * ========================================================
 */

object NumberUtil {

    fun formatNumber(value: Double): String = String.format("%.2f", value)

    fun formatNumber(value: BigDecimal): String = String.format("%.2f", value)

    private fun formatNumber(value: Any?, pattern: String): String {
        return if (value != null) {
            DecimalFormat(pattern).format(value)
        } else ""
    }

    fun formatNumberReturnInteger(value: Any?, pattern: String) = if (value != null) {
            Integer.valueOf(formatNumber(value, pattern))!!
        } else 0

    fun formatNumberReturnDouble(value: Any?, pattern: String): Double {
        if (value != null) {
            val format = DecimalFormat(pattern)
            format.roundingMode = RoundingMode.HALF_UP
            return java.lang.Double.valueOf(format.format(value))
        }
        return 0.0
    }


    // 默认除法运算精度
    private val DEF_DIV_SCALE = 10

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    fun add(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(java.lang.Double.toString(v1))
        val b2 = BigDecimal(java.lang.Double.toString(v2))
        return b1.add(b2).toDouble()
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    fun sub(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(java.lang.Double.toString(v1))
        val b2 = BigDecimal(java.lang.Double.toString(v2))
        return b1.subtract(b2).toDouble()
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    fun mul(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(java.lang.Double.toString(v1))
        val b2 = BigDecimal(java.lang.Double.toString(v2))
        return b1.multiply(b2).toDouble()
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    @JvmOverloads
    fun div(v1: Double, v2: Double, scale: Int = DEF_DIV_SCALE): Double {
        if (scale < 0) {
            throw IllegalArgumentException("The scale must be a positive integer or zero")
        }
        val b1 = BigDecimal(java.lang.Double.toString(v1))
        val b2 = BigDecimal(java.lang.Double.toString(v2))
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    fun round(v: Double, scale: Int): Double {
        if (scale < 0) {
            throw IllegalArgumentException("The scale must be a positive integer or zero")
        }
        val b = BigDecimal(java.lang.Double.toString(v))
        val one = BigDecimal("1")
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    /**
     * 提供精确的类型转换(Float)
     *
     * @param v 需要被转换的数字
     * @return 返回转换结果
     */
    fun convertsToFloat(v: Double): Float {
        val b = BigDecimal(v)
        return b.toFloat()
    }

    /**
     * 提供精确的类型转换(Int)不进行四舍五入
     *
     * @param v 需要被转换的数字
     * @return 返回转换结果
     */
    fun convertsToInt(v: Double): Int {
        val b = BigDecimal(v)
        return b.toInt()
    }

    /**
     * 提供精确的类型转换(Long)
     *
     * @param v 需要被转换的数字
     * @return 返回转换结果
     */
    fun convertsToLong(v: Double): Long {
        val b = BigDecimal(v)
        return b.toLong()
    }

    /**
     * 返回两个数中大的一个值
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 返回两个数中大的一个值
     */
    fun returnMax(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.max(b2).toDouble()
    }

    /**
     * 返回两个数中小的一个值
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 返回两个数中小的一个值
     */
    fun returnMin(v1: Double, v2: Double): Double {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.min(b2).toDouble()
    }

    /**
     * 精确对比两个数字
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1
     */
    fun compareTo(v1: Double, v2: Double): Int {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.compareTo(b2)
    }

}