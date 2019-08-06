package kv.vension.fastframe.views

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatImageView

/**
 * ========================================================
 * @author: Created by Vension on 2018/12/3 16:58.
 * @email:  250685***4@qq.com
 * @desc:   实现图像根据按下抬起动作变化颜色
 * ========================================================
 */
class ColorFilterImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr), View.OnTouchListener {

    init {
        setOnTouchListener(this)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN  // 按下时图像变灰
            -> setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY)
            MotionEvent.ACTION_UP   // 手指离开或取消操作时恢复原色
                , MotionEvent.ACTION_CANCEL -> setColorFilter(Color.TRANSPARENT)
            else -> {
            }
        }
        return false
    }

}