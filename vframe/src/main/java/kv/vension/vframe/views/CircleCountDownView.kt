package kv.vension.vframe.views

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.os.CountDownTimer
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.LinearInterpolator
import kv.vension.vframe.R

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/22 11:46
 * 描  述：自定义圆形倒计时控件
 * ========================================================
 */
class CircleCountDownView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr),View.OnClickListener {

    //定义一些常量(大小写字母切换快捷键 Ctrl + Shift + U)
    private val DEFAULT_CIRCLE_SOLIDE_COLOR = Color.parseColor("#5E4096")
    private val DEFAULT_CIRCLE_STROKE_COLOR = Color.parseColor("#00000000")//大圆边框默认透明色
    private val DEFAULT_CIRCLE_STROKE_WIDTH = 2
    private val DEFAULT_CIRCLE_RADIUS = 22

    private val PROGRESS_COLOR = Color.parseColor("#F76E6B")
    private val PROGRESS_WIDTH = 2

    private val SMALL_CIRCLE_SOLIDE_COLOR = Color.parseColor("#FFFFFF")
    private val SMALL_CIRCLE_STROKE_COLOR = Color.parseColor("#F76E6B")
    private val SMALL_CIRCLE_STROKE_WIDTH = 1
    private val SMALL_CIRCLE_RADIUS = 3

    private val TEXT_COLOR = Color.parseColor("#F76E6B")
    private val TEXT_SIZE = 13

    //默认圆
    private var defaultCircleSolideColor = DEFAULT_CIRCLE_SOLIDE_COLOR
    private var defaultCircleStrokeColor = DEFAULT_CIRCLE_STROKE_COLOR
    private var defaultCircleStrokeWidth = dp2px(DEFAULT_CIRCLE_STROKE_WIDTH)
    private var defaultCircleRadius = dp2px(DEFAULT_CIRCLE_RADIUS)//半径
    //进度条
    private var progressColor = PROGRESS_COLOR
    private var progressWidth = dp2px(PROGRESS_WIDTH)
    //默认圆边框上面的小圆
    private var smallCircleSolideColor = SMALL_CIRCLE_SOLIDE_COLOR
    private var smallCircleStrokeColor = SMALL_CIRCLE_STROKE_COLOR
    private var smallCircleStrokeWidth = dp2px(SMALL_CIRCLE_STROKE_WIDTH)
    private var smallCircleRadius = dp2px(SMALL_CIRCLE_RADIUS)
    //最里层的文字
    private var textColor = TEXT_COLOR
    private var textSize = sp2px(TEXT_SIZE)


    //画笔
    private var defaultCriclePaint: Paint? = null
    private var progressPaint: Paint? = null
    private var smallCirclePaint: Paint? = null//画小圆边框的画笔
    private var textPaint: Paint? = null
    private var defaultCricleSolidePaint: Paint? = null//画内部圆的实心的画笔
    private var smallCircleSolidePaint: Paint? = null//画小圆的实心的画笔

    //圆弧开始的角度
    private val mStartSweepValue = -90f
    //当前的角度
    private var currentAngle: Float = 0.toFloat()
    //提供一个外界可以设置的倒计时数值，毫秒值
    private var countdownTime: Long = 0
    //中间文字描述
    private var textDesc = "跳过"
    //小圆运动路径Path
    private val mPath: Path? = null

    //额外距离
    private val extraDistance = 0.7f
    private var mCountDownTimer: CountDownTimer? = null//倒计时
    private var mCountdownFinishListener : OnCountdownFinishListener? = null

    //属性动画
    private val animator by lazy {
        ValueAnimator.ofFloat(0f, 1.0f)
    }

    init {
        //获取自定义属性
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CircleCountDownView)
        initTypedArray(ta)
        //设置画笔
        setPaint()
        this.setOnClickListener(this)
    }


    private fun initTypedArray(typedArray: TypedArray) {
        defaultCircleSolideColor = typedArray.getColor(R.styleable.CircleCountDownView_default_circle_solide_color, defaultCircleSolideColor)
        defaultCircleStrokeColor = typedArray.getColor(R.styleable.CircleCountDownView_default_circle_stroke_color, defaultCircleStrokeColor)
        defaultCircleStrokeWidth = typedArray.getDimension(R.styleable.CircleCountDownView_default_circle_stroke_width, defaultCircleStrokeWidth.toFloat()).toInt()
        defaultCircleRadius = typedArray.getDimension(R.styleable.CircleCountDownView_default_circle_radius, defaultCircleRadius.toFloat()).toInt()
        progressColor = typedArray.getColor(R.styleable.CircleCountDownView_progress_color, progressColor)
        progressWidth = typedArray.getDimension(R.styleable.CircleCountDownView_progress_width, progressWidth.toFloat()).toInt()
        smallCircleSolideColor = typedArray.getColor(R.styleable.CircleCountDownView_small_circle_solide_color, smallCircleSolideColor)
        smallCircleStrokeColor = typedArray.getColor(R.styleable.CircleCountDownView_small_circle_stroke_color, smallCircleStrokeColor)
        smallCircleStrokeWidth = typedArray.getDimension(R.styleable.CircleCountDownView_small_circle_stroke_width, smallCircleStrokeWidth.toFloat()).toInt()
        smallCircleRadius = typedArray.getDimension(R.styleable.CircleCountDownView_small_circle_radius, smallCircleRadius.toFloat()).toInt()
        textColor = typedArray.getColor(R.styleable.CircleCountDownView_text_color, textColor)
        textSize = typedArray.getDimension(R.styleable.CircleCountDownView_text_size, textSize.toFloat()).toInt()
        //回收typedArray对象
        typedArray.recycle()
    }

    private fun setPaint() {
        //默认圆
        defaultCriclePaint = Paint()
        defaultCriclePaint?.isAntiAlias = true//抗锯齿
        defaultCriclePaint?.isDither = true//防抖动
        defaultCriclePaint?.style = Paint.Style.STROKE
        defaultCriclePaint?.strokeWidth = defaultCircleStrokeWidth.toFloat()
        defaultCriclePaint?.color = defaultCircleStrokeColor//这里先画边框的颜色，后续再添加画笔画实心的颜色
        //默认圆的实心画笔
        defaultCricleSolidePaint = Paint()
        defaultCricleSolidePaint?.isAntiAlias = true
        defaultCricleSolidePaint?.isDither = true
        defaultCricleSolidePaint?.style = Paint.Style.FILL
        defaultCricleSolidePaint?.color = defaultCircleSolideColor
        //默认圆上面的进度弧度
        progressPaint = Paint()
        progressPaint?.isAntiAlias = true
        progressPaint?.isDither = true
        progressPaint?.style = Paint.Style.STROKE
        progressPaint?.strokeWidth = progressWidth.toFloat()
        progressPaint?.color = progressColor
        progressPaint?.strokeCap = Paint.Cap.ROUND//设置画笔笔刷样式
        //进度上面的小圆
        smallCirclePaint = Paint()
        smallCirclePaint?.isAntiAlias = true
        smallCirclePaint?.isDither = true
        smallCirclePaint?.style = Paint.Style.STROKE
        smallCirclePaint?.strokeWidth = smallCircleStrokeWidth.toFloat()
        smallCirclePaint?.color = smallCircleStrokeColor
        //画进度上面的小圆的实心画笔（主要是将小圆的实心颜色设置成白色）
        smallCircleSolidePaint = Paint()
        smallCircleSolidePaint?.isAntiAlias = true
        smallCircleSolidePaint?.isDither = true
        smallCircleSolidePaint?.style = Paint.Style.FILL
        smallCircleSolidePaint?.color = smallCircleSolideColor

        //文字画笔
        textPaint = Paint()
        textPaint?.isAntiAlias = true
        textPaint?.isDither = true
        textPaint?.style = Paint.Style.FILL
        textPaint?.color = textColor
        textPaint?.textSize = textSize.toFloat()
    }


    /**
     * 如果该View布局的宽高开发者没有精确的告诉，则需要进行测量，如果给出了精确的宽高则我们就不管了
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpec
        var heightMeasureSpec = heightMeasureSpec
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val widthSize: Int
        val heightSize: Int
        val strokeWidth = Math.max(defaultCircleStrokeWidth, progressWidth)
        if (widthMode != View.MeasureSpec.EXACTLY) {
            widthSize = paddingLeft + defaultCircleRadius * 2 + strokeWidth + paddingRight
            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthSize, View.MeasureSpec.EXACTLY)
        }
        if (heightMode != View.MeasureSpec.EXACTLY) {
            heightSize = paddingTop + defaultCircleRadius * 2 + strokeWidth + paddingBottom
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(heightSize, View.MeasureSpec.EXACTLY)
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()
        canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())
        //画默认圆
        defaultCriclePaint?.let {
            canvas.drawCircle(
                defaultCircleRadius.toFloat(),
                defaultCircleRadius.toFloat(),
                defaultCircleRadius.toFloat(),
                it
            )
        }
        //画默认圆的实心
        defaultCricleSolidePaint?.let {
            canvas.drawCircle(
                defaultCircleRadius.toFloat(),
                defaultCircleRadius.toFloat(),
                (defaultCircleRadius - defaultCircleStrokeWidth).toFloat(),
                it
            )
        }

        //画进度圆弧
        //currentAngle = getProgress()*1.0f/getMax()*360;
        canvas.drawArc(
            RectF(0f, 0f, (defaultCircleRadius * 2).toFloat(), (defaultCircleRadius * 2).toFloat()),
            mStartSweepValue,
            360 * currentAngle,
            false,
            progressPaint!!
        )
        //画中间文字
        //   String text = getProgress()+"%";
        //获取文字的长度的方法
        val textWidth = textPaint?.measureText(textDesc)
        val textHeight = (textPaint!!.descent() + textPaint!!.ascent()) / 2
        canvas.drawText(textDesc, defaultCircleRadius - textWidth!! / 2, defaultCircleRadius - textHeight, textPaint!!)

        //画小圆
        val currentDegreeFlag = 360 * currentAngle + extraDistance
        var smallCircleX = 0f
        var smallCircleY = 0f
        val hudu = Math.abs(Math.PI * currentDegreeFlag / 180).toFloat()//Math.abs：绝对值 ，Math.PI：表示π ， 弧度 = 度*π / 180
        smallCircleX = Math.abs(Math.sin(hudu.toDouble()) * defaultCircleRadius + defaultCircleRadius).toFloat()
        smallCircleY = Math.abs(defaultCircleRadius - Math.cos(hudu.toDouble()) * defaultCircleRadius).toFloat()
        smallCirclePaint?.let { canvas.drawCircle(smallCircleX, smallCircleY, smallCircleRadius.toFloat(), it) }
        //画小圆的实心
        canvas.drawCircle(
            smallCircleX,
            smallCircleY,
            (smallCircleRadius - smallCircleStrokeWidth).toFloat(),
            smallCircleSolidePaint!!
        )

        canvas.restore()
    }


    override fun onClick(v: View?) {
        mCountdownFinishListener?.let{
            cancel()
            it.countdownFinished()
        }
    }

    //提供一个外界可以设置的倒计时数值
    fun setCountdownTime(countdownTime: Long) {
        this.countdownTime = countdownTime
        //        textDesc = countdownTime / 1000 + "″";
    }

    //属性动画
    fun startCountDownTime(countdownFinishListener: OnCountdownFinishListener?) {
        this.mCountdownFinishListener = countdownFinishListener
        //setClickable(false);开始动画之后不可点击

        //动画时长，让进度条在CountDown时间内正好从0-360走完，这里由于用的是CountDownTimer定时器，倒计时要想减到0则总时长需要多加1000毫秒，所以这里时间也跟着+1000ms
        animator.duration = countdownTime + 1000
        animator.interpolator = LinearInterpolator()//匀速
        animator.repeatCount = 0//表示不循环，-1表示无限循环
        //值从0-1.0F 的动画，动画时长为countdownTime，ValueAnimator没有跟任何的控件相关联，那也正好说明ValueAnimator只是对值做动画运算，而不是针对控件的，我们需要监听ValueAnimator的动画过程来自己对控件做操作
        //添加监听器,监听动画过程中值的实时变化(animation.getAnimatedValue()得到的值就是0-1.0)
        animator.addUpdateListener { animation ->
            /**
             * 这里我们已经知道ValueAnimator只是对值做动画运算，而不是针对控件的，因为我们设置的区间值为0-1.0f
             * 所以animation.getAnimatedValue()得到的值也是在[0.0-1.0]区间，而我们在画进度条弧度时，设置的当前角度为360*currentAngle，
             * 因此，当我们的区间值变为1.0的时候弧度刚好转了360度
             */
            currentAngle = animation.animatedValue as Float
            //       Log.e("currentAngle",currentAngle+"");
            invalidate()//实时刷新view，这样我们的进度条弧度就动起来了
        }
        //开启动画
        animator.start()
        //还需要另一个监听，监听动画状态的监听器
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                clearAnimation()
                //倒计时结束的时候，需要通过自定义接口通知UI去处理其他业务逻辑
                mCountdownFinishListener?.let {
                    it.countdownFinished()
                }
                isClickable = countdownTime > 0
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {}
        })
        //调用倒计时操作
        countdownMethod()
    }

    //倒计时的方法
    private fun countdownMethod() {
        mCountDownTimer = object : CountDownTimer(countdownTime + 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countdownTime -= 1000
                textDesc = (countdownTime/1000).toString() + "s"
                Log.e("倒计时",countdownTime.toString() + "")
                //刷新view
                invalidate()
            }

            override fun onFinish() {
                textDesc = "0s"
                //同时隐藏小球
                smallCirclePaint?.color = resources.getColor(android.R.color.transparent)
                smallCircleSolidePaint?.color = resources.getColor(android.R.color.transparent)
                //刷新view
                invalidate()
            }
        }.start()
    }


    /**
     * 取消倒计时
     */
    fun cancel() {
        animator.removeAllListeners()
        clearAnimation()
        mCountDownTimer?.let {
            mCountDownTimer?.cancel()
        }
    }

    /**
     *  通过自定义接口通知UI去处理其他业务逻辑
     */
    interface OnCountdownFinishListener {
        fun countdownFinished()
    }


    /**
     * dp 2 px
     *
     * @param dpVal
     */
    private fun dp2px(dpVal: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal.toFloat(), resources.displayMetrics).toInt()
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    private fun sp2px(spVal: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal.toFloat(), resources.displayMetrics).toInt()
    }

}