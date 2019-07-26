package kv.vension.vframe.views

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min






/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/1 17:53
 * 描  述：圆形、矩形ImageView
 * ========================================================
 */

class ShapeImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {

    private val mType: Int //类型
    private val mBorderColor: Int //边框颜色
    private val mBorderWidth: Int //边框宽度
    private val mRectRoundRadius: Int //圆角大小
    private var isGradientBorder: Boolean //边框颜色是否渐变
    private var graStartColor: Int //边框渐变开始颜色
    private var graCenterColor: Int //边框渐变中间颜色
    private var graEndColor: Int //边框渐变结束颜色

    private val mPaintBitmap = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPaintBorder = Paint(Paint.ANTI_ALIAS_FLAG)

    private val mRectBorder = RectF()
    private val mRectBitmap = RectF()

    private var mRawBitmap: Bitmap? = null
    private var mShader: BitmapShader? = null
    private val mMatrix = Matrix()

    init {
        super.setScaleType(ScaleType.CENTER_CROP)
        //取xml文件中设定的参数
        val ta = context.obtainStyledAttributes(attrs, kv.vension.vframe.R.styleable.ShapeImageView)
        mType = ta.getInt(kv.vension.vframe.R.styleable.ShapeImageView_siv_type, DEFAULT_TYPE)
        mBorderColor = ta.getColor(kv.vension.vframe.R.styleable.ShapeImageView_siv_borderColor, DEFAULT_BORDER_COLOR)
        mBorderWidth = ta.getDimensionPixelSize(kv.vension.vframe.R.styleable.ShapeImageView_siv_borderWidth, dp2px(DEFAULT_BORDER_WIDTH))
        mRectRoundRadius = ta.getDimensionPixelSize(kv.vension.vframe.R.styleable.ShapeImageView_siv_rectRoundRadius, dp2px(DEFAULT_RECT_ROUND_RADIUS))
        isGradientBorder = ta.getBoolean(kv.vension.vframe.R.styleable.ShapeImageView_siv_isGradientBorder, false)
        graStartColor = ta.getColor(kv.vension.vframe.R.styleable.ShapeImageView_siv_graStartColor, Color.BLUE)
        graCenterColor = ta.getColor(kv.vension.vframe.R.styleable.ShapeImageView_siv_graCenterColor, Color.RED)
        graEndColor = ta.getColor(kv.vension.vframe.R.styleable.ShapeImageView_siv_graEndColor, Color.GREEN)
        ta.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        val rawBitmap = getBitmap(drawable)

        if (rawBitmap != null && mType != TYPE_NONE) {
            val viewWidth = width
            val viewHeight = height
            val viewMinSize = min(viewWidth, viewHeight)
            val dstWidth = (if (mType == TYPE_CIRCLE) viewMinSize else viewWidth).toFloat()
            val dstHeight = (if (mType == TYPE_CIRCLE) viewMinSize else viewHeight).toFloat()
            val halfBorderWidth = mBorderWidth / 2.0f
            val doubleBorderWidth = (mBorderWidth * 2).toFloat()

            if (mShader == null || rawBitmap != mRawBitmap) {
                mRawBitmap = rawBitmap
                mShader = BitmapShader(mRawBitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            }
            if (mShader != null) {
                mMatrix.setScale((dstWidth - doubleBorderWidth) / rawBitmap.width, (dstHeight - doubleBorderWidth) / rawBitmap.height)
                mShader!!.setLocalMatrix(mMatrix)
            }

            mPaintBitmap.shader = mShader
            mPaintBorder.style = Paint.Style.STROKE
            mPaintBorder.strokeWidth = mBorderWidth.toFloat()
            //是否渐变
            if(isGradientBorder){
                // 创建SweepGradient对象, 第一个,第二个参数中心坐标,后面的参数与线性渲染相同
                val mGradient = SweepGradient(0f, 0f, intArrayOf(graStartColor,graCenterColor,graEndColor,graStartColor), floatArrayOf (0f, 0.45f, 0.9f, 1f))
                val gradientMatrix = Matrix()
                gradientMatrix.setTranslate(dstWidth/2,dstWidth/2)
                mGradient.setLocalMatrix(gradientMatrix)
                mPaintBorder.shader = mGradient
                mPaintBorder.maskFilter = BlurMaskFilter(15f, BlurMaskFilter.Blur.SOLID)//设置发光
            }else{
                mPaintBorder.color = if (mBorderWidth > 0) mBorderColor else Color.TRANSPARENT
            }

            if (mType == TYPE_CIRCLE) {
                val radius = viewMinSize / 2.0f
                canvas.drawCircle(radius, radius, radius - halfBorderWidth, mPaintBorder)
                canvas.translate(mBorderWidth.toFloat(), mBorderWidth.toFloat())
                canvas.drawCircle(radius - mBorderWidth, radius - mBorderWidth, radius - mBorderWidth, mPaintBitmap)
            } else if (mType == TYPE_ROUNDED_RECT) {
                mRectBorder.set(halfBorderWidth, halfBorderWidth, dstWidth - halfBorderWidth, dstHeight - halfBorderWidth)
                mRectBitmap.set(0.0f, 0.0f, dstWidth - doubleBorderWidth, dstHeight - doubleBorderWidth)
                val borderRadius = if (mRectRoundRadius - halfBorderWidth > 0.0f) mRectRoundRadius - halfBorderWidth else 0.0f
                val bitmapRadius = if (mRectRoundRadius - mBorderWidth > 0.0f) mRectRoundRadius - mBorderWidth else 0.0f

                canvas.drawRoundRect(mRectBorder, borderRadius, borderRadius, mPaintBorder)
                canvas.translate(mBorderWidth.toFloat(), mBorderWidth.toFloat())
                canvas.drawRoundRect(mRectBitmap, bitmapRadius as Float, bitmapRadius, mPaintBitmap)
            }
        } else {
            super.onDraw(canvas)
        }
    }

    private fun dp2px(dipVal: Int): Int {
        val scale = resources.displayMetrics.density
        return (dipVal * scale + 0.5f).toInt()
    }

    private fun getBitmap(drawable: Drawable): Bitmap? {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        } else if (drawable is ColorDrawable) {
            val rect = drawable.getBounds()
            val width = rect.right - rect.left
            val height = rect.bottom - rect.top
            val color = drawable.color
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            canvas.drawARGB(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color))
            return bitmap
        } else {
            return null
        }
    }

    companion object {
        /**
         * android.widget.ImageView
         */
        val TYPE_NONE = 0
        /**
         * 圆形
         */
        val TYPE_CIRCLE = 1
        /**
         * 圆角矩形
         */
        val TYPE_ROUNDED_RECT = 2

        private val DEFAULT_TYPE = TYPE_NONE
        private val DEFAULT_BORDER_COLOR = Color.TRANSPARENT
        private val DEFAULT_BORDER_WIDTH = 0
        private val DEFAULT_RECT_ROUND_RADIUS = 0
    }
}