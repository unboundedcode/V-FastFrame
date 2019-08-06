package kv.vension.fastframe.glide.transformation

import android.graphics.Bitmap
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import java.security.MessageDigest

/**
 * ========================================================
 * @author: Created by Vension on 2018/12/4 14:53.
 * @email:  250685***4@qq.com
 * @desc:   圆形效果
 * ========================================================
 */ 
class CircleBitmapTransformation : BitmapTransformation(){

    private val VERSION = 1

    private val ID = CircleBitmapTransformation::class.java!!.getName() + VERSION

    private val ID_BYTES = ID.toByteArray(Key.CHARSET)

    override fun equals(other: Any?): Boolean {
        return other is CircleBitmapTransformation
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        return TransformationUtils.circleCrop(pool, toTransform, outWidth, outHeight)
    }

}