package kv.vension.vframe.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/12/17 11:30
 * 描  述：Bitmap 压缩工具类
 * ========================================================
 */

object BitmapUtil {

    /**
     * 将原图压缩为大概width*height个像素，
     * 原图像素数/(width*height)=压缩比率^2，得到这个比率后，对原图进行等比例压缩；
     * 避免的误区是，以为放入的width*height就是要压缩到的大小
     **/
    fun getBitmapBySize(path: String, width: Int, height: Int): Bitmap? {
        val option = BitmapFactory.Options()
        option.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, option)
        option.inSampleSize = computeSampleSize(option, -1, width * height)

        option.inJustDecodeBounds = false
        var bitmap: Bitmap? = null
        try {
            bitmap = BitmapFactory.decodeFile(path, option)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return bitmap
    }


    fun computeSampleSize(options: BitmapFactory.Options, minSideLength: Int, maxNumOfPixels: Int): Int {
        val initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels)
        var roundedSize: Int
        if (initialSize <= 8) {
            roundedSize = 1
            while (roundedSize < initialSize) {
                roundedSize = roundedSize shl 1
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8
        }
        return roundedSize
    }

    private fun computeInitialSampleSize(options: BitmapFactory.Options, minSideLength: Int, maxNumOfPixels: Int): Int {
        val w = options.outWidth.toDouble()
        val h = options.outHeight.toDouble()
        val lowerBound = if (maxNumOfPixels == -1) 1 else Math.ceil(Math.sqrt(w * h / maxNumOfPixels)).toInt()
        val upperBound = if (minSideLength == -1) 128 else Math.min(
            Math.floor(w / minSideLength),
            Math.floor(h / minSideLength)
        ).toInt()
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound
        }
        return if (maxNumOfPixels == -1 && minSideLength == -1) {
            1
        } else if (minSideLength == -1) {
            lowerBound
        } else {
            upperBound
        }
    }
}