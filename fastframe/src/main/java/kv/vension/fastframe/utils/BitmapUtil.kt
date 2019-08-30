package kv.vension.fastframe.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import androidx.annotation.DrawableRes
import java.io.*
import kotlin.math.roundToInt


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/12/17 11:30
 * 描  述：Bitmap 转换压缩工具类
 * ========================================================
 */

object BitmapUtil {

    private const val REQ_PHOTO_CAMERA = 110 // 拍照
    private const val REQ_PHOTO_ALBUM = 120 // 相册
    private const val REQ_PHOTO_CROP = 130 // 裁剪

    /**
     * 计算图片的缩放值
     *
     * @param options   options
     * @param reqWidth  压缩后的宽度
     * @param reqHeight 压缩后的高度
     * @return 压缩比例
     */
    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // 源图片的高度和宽度
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt()
            val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt()
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        return inSampleSize
    }

    /**
     * 尺寸压缩资源文件
     *
     * @param res       resource
     * @param resId     id
     * @param reqWidth  压缩后的宽度
     * @param reqHeight 压缩后的高度
     * @return 压缩后的bitmap
     */
    fun sizeCompress(res: Resources, resId: Int, reqWidth: Int, reqHeight: Int): Bitmap {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)
        // 调用上面定义的方法计算缩放值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }


    /**
     * 根据路径获得图片并按尺寸压缩，返回bitmap用于显示
     *
     * @param filePath  filePath
     * @param reqWidth  reqWidth
     * @param reqHeight reqHeight
     * @return 压缩后的bitmap
     */
    fun sizeCompress(filePath: String, reqWidth: Int, reqHeight: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, options)
        //计算缩放值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        //压缩
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(filePath, options)
    }

    /**
     * 根据图片并按尺寸压缩，返回bitmap用于显示
     *
     * @param bitmap    bitmap
     * @param reqWidth  reqWidth
     * @param reqHeight reqHeight
     * @return
     */
    fun sizeCompress(bitmap: Bitmap, reqWidth: Int, reqHeight: Int): Bitmap {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        baos.reset()
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(data, 0, data.size, options)
        //计算缩放值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        //压缩
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeByteArray(data, 0, data.size, options)
    }

    /**
     * 质量压缩Bitmap,并保存至指定文件
     * 这个方法只会改变图片的存储大小,不会改变bitmap的大小
     *
     * @param bitmap  bitmap
     * @param maxSize 最大的大小，单位KB
     * @return 是否压缩并保存成功
     */
    fun qualityCompress(bitmap: Bitmap, maxSize: Int, fileToSave: File): Boolean {
        val baos = ByteArrayOutputStream()
        var options = 100
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos)

        while (baos.toByteArray().size / 1024 > maxSize) {
            Log.e("BitmapUtil","bytes1: " + baos.toByteArray().size / 1024 + " KB")
            baos.reset()
            if (options > 10) {
                options -= 15
            } else {
                options -= 1
            }
            if (options == 0) {
                break
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos)
        }
        val bytes = baos.toByteArray()
        Log.e("BitmapUtil","bytes2: " + bytes.size / 1024 + " KB")

        try {
            val fos = FileOutputStream(fileToSave)
            try {
                fos.write(bytes)
                fos.flush()
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
                return false
            }

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return false
        }

        return true
    }

    /**
     * 保存Bitmaod到目标文件
     * @param bitmap 要保存的bitmap
     * @param file   目标文件
     */
    fun saveBitmapToFile(bitmap: Bitmap, file: File): Boolean {
        var outputStream: FileOutputStream? = null
        try {
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            try {
                outputStream!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return true
    }


    /**
     * 使用相机拍照并保存到指定位置
     * @param activity
     * @param uri 要保存的文件Uri，请确保该Uri兼容7.0系统（可使用FileUtil.getUriForFile()来获取Uri）
     */
    fun getImageFromCamera(activity: Activity, uri: Uri) {
        val openCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        activity.startActivityForResult(openCameraIntent, REQ_PHOTO_CAMERA)
    }

    /**
     * 从手机相册中获取照片
     * @param activity
     */
    fun getImageFromAlbums(activity: Activity) {
        val openAlbumIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        activity.startActivityForResult(openAlbumIntent, REQ_PHOTO_ALBUM)
    }

    /**
     * 使用系统的裁剪图片，并保存至指定位置
     * @param activity
     * @param cropWidth 裁剪后图片的宽度
     * @param cropHeight 裁剪后图片的高度
     * @param photoUri 要裁剪的图片文件Uri，请确保该Uri兼容7.0系统（可使用FileUtil.getUriForFile()来获取Uri）
     * @param fileToSave 裁剪后的图片将保存到该文件中
     */
    fun cropImage(activity: Activity, cropWidth: Int, cropHeight: Int, photoUri: Uri, fileToSave: File) {
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(photoUri, "image/*")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }

        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true")
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", cropWidth)
        intent.putExtra("outputY", cropHeight)
        intent.putExtra("return-data", false)
        intent.putExtra("noFaceDetection", true)
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileToSave))
        activity.startActivityForResult(intent, REQ_PHOTO_CROP)
    }


    //得到图片应该调整的度数，用于调整照片方向
    fun getFixRotate(path: String): Int {
        var degree = 0
        try {
            val exifInterface = ExifInterface(path)
            val orientation = exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return degree
    }

    /**
     * 将文件转换成bitmap
     *
     * @param filePath 文件名
     * @return Bitmap
     */
    fun fileToBitmap(filePath: String): Bitmap {
        return BitmapFactory.decodeFile(filePath)
    }

    /**
     * 资源文件转为bitmap
     *
     * @param context 上下文
     * @param id      资源id
     * @return bitmap
     */
    fun resourcesToBitmap(context: Context, @DrawableRes id: Int): Bitmap {
        val resources = context.resources
        return BitmapFactory.decodeResource(resources, id)
    }

    /**
     * 根据uri转为bitmap
     *
     * @param context context
     * @param uri     uri
     * @return btimap
     */
    fun decodeUriAsBitmap(context: Context?, uri: Uri?): Bitmap? {
        if (context == null || uri == null) return null

        val bitmap: Bitmap
        try {
            bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return null
        }

        return bitmap
    }

    /**
     * 使用RenderScript实现的高斯模糊效果（性能较高，模糊半径0-25，越大越模糊）
     * 需在module下的build.gradle中加入以下配置才可使用
     * renderscriptTargetApi 19
     * renderscriptSupportModeEnabled true
     */
    fun rsBlur(context: Context, bitmap: Bitmap, radius: Int): Bitmap {
        var rs: RenderScript? = null
        try {
            rs = RenderScript.create(context)
            val input = Allocation.createFromBitmap(rs, bitmap)
            val output = Allocation.createTyped(rs, input.type)
            val blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))

            blur.setRadius(radius.toFloat())
            blur.setInput(input)
            blur.forEach(output)
            output.copyTo(bitmap)
        } finally {
            rs?.destroy()
        }

        return bitmap
    }

    /**
     *  使用Java实现的高斯模糊效果（性能较低，模糊半径0-100，越大越模糊）
     */
    fun fastBlur(sentBitmap: Bitmap, radius: Int, canReuseInBitmap: Boolean): Bitmap? {

        val bitmap: Bitmap
        if (canReuseInBitmap) {
            bitmap = sentBitmap
        } else {
            bitmap = sentBitmap.copy(sentBitmap.config, true)
        }

        if (radius < 1) {
            return null
        }

        val w = bitmap.width
        val h = bitmap.height

        val pix = IntArray(w * h)
        bitmap.getPixels(pix, 0, w, 0, 0, w, h)

        val wm = w - 1
        val hm = h - 1
        val wh = w * h
        val div = radius + radius + 1

        val r = IntArray(wh)
        val g = IntArray(wh)
        val b = IntArray(wh)
        var rsum: Int
        var gsum: Int
        var bsum: Int
        var x: Int
        var y: Int
        var i: Int
        var p: Int
        var yp: Int
        var yi: Int
        var yw: Int
        val vmin = IntArray(Math.max(w, h))

        var divsum = div + 1 shr 1
        divsum *= divsum
        val dv = IntArray(256 * divsum)
        i = 0
        while (i < 256 * divsum) {
            dv[i] = i / divsum
            i++
        }

        yi = 0
        yw = yi

        val stack = Array(div) { IntArray(3) }
        var stackpointer: Int
        var stackstart: Int
        var sir: IntArray
        var rbs: Int
        val r1 = radius + 1
        var routsum: Int
        var goutsum: Int
        var boutsum: Int
        var rinsum: Int
        var ginsum: Int
        var binsum: Int

        y = 0
        while (y < h) {
            bsum = 0
            gsum = bsum
            rsum = gsum
            boutsum = rsum
            goutsum = boutsum
            routsum = goutsum
            binsum = routsum
            ginsum = binsum
            rinsum = ginsum
            i = -radius
            while (i <= radius) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))]
                sir = stack[i + radius]
                sir[0] = p and 0xff0000 shr 16
                sir[1] = p and 0x00ff00 shr 8
                sir[2] = p and 0x0000ff
                rbs = r1 - Math.abs(i)
                rsum += sir[0] * rbs
                gsum += sir[1] * rbs
                bsum += sir[2] * rbs
                if (i > 0) {
                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]
                } else {
                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]
                }
                i++
            }
            stackpointer = radius

            x = 0
            while (x < w) {

                r[yi] = dv[rsum]
                g[yi] = dv[gsum]
                b[yi] = dv[bsum]

                rsum -= routsum
                gsum -= goutsum
                bsum -= boutsum

                stackstart = stackpointer - radius + div
                sir = stack[stackstart % div]

                routsum -= sir[0]
                goutsum -= sir[1]
                boutsum -= sir[2]

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm)
                }
                p = pix[yw + vmin[x]]

                sir[0] = p and 0xff0000 shr 16
                sir[1] = p and 0x00ff00 shr 8
                sir[2] = p and 0x0000ff

                rinsum += sir[0]
                ginsum += sir[1]
                binsum += sir[2]

                rsum += rinsum
                gsum += ginsum
                bsum += binsum

                stackpointer = (stackpointer + 1) % div
                sir = stack[stackpointer % div]

                routsum += sir[0]
                goutsum += sir[1]
                boutsum += sir[2]

                rinsum -= sir[0]
                ginsum -= sir[1]
                binsum -= sir[2]

                yi++
                x++
            }
            yw += w
            y++
        }
        x = 0
        while (x < w) {
            bsum = 0
            gsum = bsum
            rsum = gsum
            boutsum = rsum
            goutsum = boutsum
            routsum = goutsum
            binsum = routsum
            ginsum = binsum
            rinsum = ginsum
            yp = -radius * w
            i = -radius
            while (i <= radius) {
                yi = Math.max(0, yp) + x

                sir = stack[i + radius]

                sir[0] = r[yi]
                sir[1] = g[yi]
                sir[2] = b[yi]

                rbs = r1 - Math.abs(i)

                rsum += r[yi] * rbs
                gsum += g[yi] * rbs
                bsum += b[yi] * rbs

                if (i > 0) {
                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]
                } else {
                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]
                }

                if (i < hm) {
                    yp += w
                }
                i++
            }
            yi = x
            stackpointer = radius
            y = 0
            while (y < h) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] =
                    -0x1000000 and pix[yi] or (dv[rsum] shl 16) or (dv[gsum] shl 8) or dv[bsum]

                rsum -= routsum
                gsum -= goutsum
                bsum -= boutsum

                stackstart = stackpointer - radius + div
                sir = stack[stackstart % div]

                routsum -= sir[0]
                goutsum -= sir[1]
                boutsum -= sir[2]

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w
                }
                p = x + vmin[y]

                sir[0] = r[p]
                sir[1] = g[p]
                sir[2] = b[p]

                rinsum += sir[0]
                ginsum += sir[1]
                binsum += sir[2]

                rsum += rinsum
                gsum += ginsum
                bsum += binsum

                stackpointer = (stackpointer + 1) % div
                sir = stack[stackpointer]

                routsum += sir[0]
                goutsum += sir[1]
                boutsum += sir[2]

                rinsum -= sir[0]
                ginsum -= sir[1]
                binsum -= sir[2]

                yi += w
                y++
            }
            x++
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h)

        return bitmap
    }

}