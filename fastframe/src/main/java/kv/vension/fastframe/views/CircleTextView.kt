package kv.vension.fastframe.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Gravity
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import kv.vension.fastframe.R


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/10/30 16:33
 * 描  述：圆形textview
 * ========================================================
 */

class CircleTextView : AppCompatTextView {

    private var circlePaint: Paint
    private var backPaint: Paint
    private var textPaint: Paint
    private var strokeColor = Color.WHITE
    private var circleBgColor = Color.WHITE
    private var strokeWidth: Float = 0.toFloat()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        gravity = Gravity.CENTER
        circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        circlePaint.style = Paint.Style.STROKE
        backPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        backPaint.style = Paint.Style.FILL
        textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        strokeWidth = 0f
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleTextView)
            strokeColor = typedArray.getColor(R.styleable.CircleTextView_ct_strokeColor, strokeColor)
            circleBgColor = typedArray.getColor(R.styleable.CircleTextView_ct_bgColor, circleBgColor)
            strokeWidth = typedArray.getDimension(R.styleable.CircleTextView_ct_strokeWidth, strokeWidth)
            typedArray.recycle()
        }
        if (strokeWidth != 0f) {
            circlePaint.strokeWidth = strokeWidth
            circlePaint.color = strokeColor
        }
        backPaint.color = circleBgColor
        textPaint.color = currentTextColor
        textPaint.textSize = textSize
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val height = height
        val width = width
        var radius: Int
        val storkRadius: Int
        val textWidth = textPaint.measureText(text.toString()).toInt()
        if (width > height) {
            if (height > textWidth) {
                radius = height
            } else {
                setHeight(textWidth + paddingTop + paddingBottom)
                radius = textWidth
            }
        } else {
            if (width > textWidth) {
                radius = width
            } else {
                setWidth(textWidth + paddingRight + paddingLeft)
                radius = textWidth
            }
        }
        storkRadius = (radius / 2 - strokeWidth).toInt()
        radius = storkRadius - 1
        if (strokeWidth != 0f)
            canvas.drawCircle((getWidth() / 2).toFloat(), (getHeight() / 2).toFloat(), storkRadius.toFloat(), circlePaint)
        canvas.drawCircle((getWidth() / 2).toFloat(), (getHeight() / 2).toFloat(), radius.toFloat(), backPaint)
        val fontMetrics = textPaint.fontMetrics
        canvas.drawText(text.toString(), getWidth() / 2 - textPaint.measureText(text.toString()) / 2, getHeight() / 2 - fontMetrics.descent + (fontMetrics.bottom - fontMetrics.top) / 2, textPaint)

    }

    fun setstrokeColor(@ColorInt color: Int) {
        this.strokeColor = color
        circlePaint.color = strokeColor
        invalidate()
    }

    fun setBackColor(@ColorInt color: Int) {
        this.circleBgColor = color
        backPaint.color = circleBgColor
        invalidate()
    }
}