package kv.vension.fastframe.image.glide.transformation

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
 * @desc:   矩形效果
 * ========================================================
 */ 
class RoundBitmapTransformation : BitmapTransformation{

    private val VERSION = 1

    private val ID = RoundBitmapTransformation::class.java!!.getName() + VERSION

    private val ID_BYTES = ID.toByteArray(Key.CHARSET)

    private val radius: Int

    constructor (radius: Int) {
        this.radius = radius
    }

    override fun equals(other: Any?): Boolean {
        return other is RoundBitmapTransformation
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        return if (radius > 0) {
            TransformationUtils.roundedCorners(
                pool,
                Bitmap.createScaledBitmap(toTransform, outWidth, outHeight, false),
                radius
            )
        } else toTransform
    }

}