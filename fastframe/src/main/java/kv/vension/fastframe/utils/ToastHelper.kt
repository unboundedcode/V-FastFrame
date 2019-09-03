package kv.vension.fastframe.utils

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import kv.vension.fastframe.R
import kv.vension.fastframe.VFrame

/**
 * ========================================================================
 * 作 者：Vension
 * 主 页：Github: https://github.com/Vension
 * 日 期：2019/9/3 10:58
 * ______       _____ _______ /\/|_____ _____            __  __ ______
 * |  ____/\    / ____|__   __|/\/  ____|  __ \     /\   |  \/  |  ____|
 * | |__ /  \  | (___    | |     | |__  | |__) |   /  \  | \  / | |__
 * |  __/ /\ \  \___ \   | |     |  __| |  _  /   / /\ \ | |\/| |  __|
 * | | / ____ \ ____) |  | |     | |    | | \ \  / ____ \| |  | | |____
 * |_|/_/    \_\_____/   |_|     |_|    |_|  \_\/_/    \_\_|  |_|______|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * 描述：自定义吐司弹窗
 * ========================================================================
 */
class ToastHelper private constructor(){

    private var icon = R.drawable.ic_warning_white_24dp
    private var message = "No text!"
    private var cardBackgroundColor = android.R.color.black
    private var cardElevation = 4f
    private var cardCornerRadius = 8f
    private var textSize = 16f
    private var typeface = Typeface.DEFAULT
    private var gravity = -91
    private var xOffset: Int = 0
    private var yOffset: Int = 0

    fun setIcon(icon: Int): ToastHelper {
        this.icon = icon
        return this
    }

    fun setMessage(message: String): ToastHelper {
        this.message = message
        return this
    }

    fun setCardBackgroundColor(cardBackgroundColor: Int): ToastHelper {
        this.cardBackgroundColor = cardBackgroundColor
        return this
    }

    fun setCardElevation(elevation: Float): ToastHelper {
        this.cardElevation = elevation
        return this
    }

    fun setCardRadius(radius: Float): ToastHelper {
        this.cardCornerRadius = radius
        return this
    }

    fun setTextSize(textSize: Float): ToastHelper {
        this.textSize = textSize
        return this
    }

    fun setTypeFace(typeface: Typeface): ToastHelper {
        this.typeface = typeface
        return this
    }

    fun setGravity(gravity: Int, xOffset: Int, yOffset: Int): ToastHelper {
        this.gravity = gravity
        this.xOffset = xOffset
        this.yOffset = yOffset
        return this
    }

    /**
     * Creates the toast with initialized variables. And returns the custom toast.
     * @param duration Duration of the toast message.
     * @param isVertical
     * @return Toast object.
     */
    fun createToast(duration: Int, isVertical: Boolean): Toast {
        val inflater = VFrame.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.layout_toast_default, null)
        val cardView = view.findViewById<CardView>(R.id.cv_toast)
        val layout = view.findViewById<LinearLayout>(R.id.ll_toast)
        val toastIcon = view.findViewById<ImageView>(R.id.iv_toastIcon)
        val toastMessage = view.findViewById<TextView>(R.id.tv_toastMsg)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cardView.setCardBackgroundColor(VFrame.getColor(cardBackgroundColor))
        } else {
            cardView.setBackgroundColor(VFrame.getColor(cardBackgroundColor))
        }

        cardView.cardElevation = cardElevation
        cardView.radius = cardCornerRadius

        if (isVertical) {//竖向
            cardView.setContentPadding(30, 40, 30, 40)
            layout.orientation = LinearLayout.VERTICAL
            val params = toastMessage.layoutParams as LinearLayout.LayoutParams
            params.setMargins(0, 10, 0, 0)
            toastMessage.layoutParams = params
        }
        toastIcon.setImageDrawable(ContextCompat.getDrawable(VFrame.mContext, icon))
        toastMessage.textSize = textSize
        toastMessage.text = message
        toastMessage.typeface = typeface

        val toast = Toast(VFrame.mContext)
        toast.view = view
        toast.duration = duration
        if (gravity != -91) toast.setGravity(gravity, xOffset, yOffset)
        return toast
    }

    companion object {

        fun normal(message: String): Toast {
            return normal(message,false)
        }
        fun normal(gravity: Int,message: String): Toast {
            return normal(Gravity.CENTER,message,false)
        }
        fun normal(message: String,isVertical: Boolean): Toast {
            return normal(Gravity.CENTER,message,isVertical)
        }

        /**
         * Pre-Set Centered Info toast
         * @param gravity
         * @param icon
         * @param message Message that will be on the toast.
         * @param bgColor
         * @param duration Duration of the toast message.
         * @param isVertical
         * @return Toast.
         */
        fun normal(gravity: Int, message: String, isVertical: Boolean): Toast {
            return ToastHelper()
                .setGravity(gravity,0, 0)
                .setMessage(message)
                .createToast(Toast.LENGTH_LONG, isVertical)
        }

        fun info(message: String): Toast {
            return info(message,false)
        }
        fun info(gravity: Int,message: String): Toast {
            return info(gravity,message,Toast.LENGTH_LONG,false)
        }
        fun info(message: String,isVertical: Boolean): Toast {
            return info(Gravity.CENTER,message,Toast.LENGTH_LONG,isVertical)
        }
        fun info(gravity: Int, message: String, duration: Int,isVertical: Boolean): Toast {
            return info(gravity,R.drawable.ic_info_black_24dp,message,R.color.color_deep_purple,duration,isVertical)
        }
        /**
         * Pre-Set Centered Info toast
         * @param gravity
         * @param icon
         * @param message Message that will be on the toast.
         * @param bgColor
         * @param duration Duration of the toast message.
         * @param isVertical
         * @return Toast.
         */
        fun info(gravity: Int,icon:Int, message: String,bgColor:Int, duration: Int,isVertical: Boolean): Toast {
            return ToastHelper()
                .setGravity(gravity,0, 0)
                .setIcon(icon)
                .setMessage(message)
                .setCardBackgroundColor(bgColor)
                .createToast(duration, isVertical)
        }


        fun warning(message: String): Toast {
            return warning(message,false)
        }
        fun warning(gravity: Int,message: String): Toast {
            return warning(gravity,message,Toast.LENGTH_LONG,false)
        }
        fun warning(message: String,isVertical: Boolean): Toast {
            return warning(Gravity.CENTER,message,Toast.LENGTH_LONG,isVertical)
        }
        fun warning(gravity: Int, message: String, duration: Int,isVertical: Boolean): Toast {
            return warning(gravity,R.drawable.ic_warning_white_24dp,message,R.color.color_orange,duration,isVertical)
        }
        /**
         * Pre-Set Centered warning toast
         * @param gravity
         * @param icon
         * @param message Message that will be on the toast.
         * @param bgColor
         * @param duration Duration of the toast message.
         * @param isVertical
         * @return Toast.
         */
        fun warning(gravity: Int,icon:Int, message: String,bgColor:Int, duration: Int,isVertical: Boolean): Toast {
            return ToastHelper()
                .setGravity(gravity,0, 0)
                .setIcon(icon)
                .setMessage(message)
                .setCardBackgroundColor(bgColor)
                .createToast(duration, isVertical)
        }


        fun error(message: String): Toast {
            return error(message,false)
        }
        fun error(gravity: Int,message: String): Toast {
            return error(gravity,message,Toast.LENGTH_LONG,false)
        }
        fun error(message: String,isVertical: Boolean): Toast {
            return error(Gravity.CENTER,message,Toast.LENGTH_LONG,isVertical)
        }
        fun error(gravity: Int, message: String, duration: Int,isVertical: Boolean): Toast {
            return error(gravity,R.drawable.ic_error_black_24dp,message,R.color.color_red,duration,isVertical)
        }
        /**
         * Pre-Set Centered warning toast
         * @param gravity
         * @param icon
         * @param message Message that will be on the toast.
         * @param bgColor
         * @param duration Duration of the toast message.
         * @param isVertical
         * @return Toast.
         */
        fun error(gravity: Int,icon:Int, message: String,bgColor:Int, duration: Int,isVertical: Boolean): Toast {
            return ToastHelper()
                .setGravity(gravity,0, 0)
                .setIcon(icon)
                .setMessage(message)
                .setCardBackgroundColor(bgColor)
                .createToast(duration, isVertical)
        }


        fun success(message: String): Toast {
            return success(message,false)
        }
        fun success(gravity: Int,message: String): Toast {
            return success(gravity,message,Toast.LENGTH_LONG,false)
        }
        fun success(message: String,isVertical: Boolean): Toast {
            return success(Gravity.CENTER,message,Toast.LENGTH_LONG,isVertical)
        }
        fun success(gravity: Int, message: String, duration: Int,isVertical: Boolean): Toast {
            return success(gravity,R.drawable.ic_success_circle_black_24dp,message,R.color.color_green,duration,isVertical)
        }
        /**
         * Pre-Set Centered error toast
         * @param gravity
         * @param icon
         * @param message Message that will be on the toast.
         * @param bgColor
         * @param duration Duration of the toast message.
         * @param isVertical
         * @return Toast.
         */
        fun success(gravity: Int,icon:Int, message: String,bgColor:Int, duration: Int,isVertical: Boolean): Toast {
            return ToastHelper()
                .setGravity(gravity,0, 0)
                .setIcon(icon)
                .setMessage(message)
                .setCardBackgroundColor(bgColor)
                .createToast(duration, isVertical)
        }



        /**
         * Customizable toast
         * @param context Context
         * @param message Message that will be on the toast.
         * @param icon Icon that will be on the left side of the toast message.
         * @param cardBackgroundColor Background color of toast.
         * @param duration Duration of the toast message.
         * @return Toast.
         */
        fun custom(gravity: Int, message: String, icon: Int, cardBackgroundColor: Int, duration: Int): Toast {
            return ToastHelper()
                .setGravity(gravity,0,0)
                .setIcon(icon)
                .setMessage(message)
                .setCardBackgroundColor(cardBackgroundColor)
                .createToast(duration, false)
        }


        /**
         * Fully customizable toast
         * @param context Context
         * @param message Message that will be on the toast.
         * @param icon Icon that will be on the left side of the toast message.
         * @param cardBackgroundColor Background color of toast.
         * @param cornerRadius Corner radius of the toast.
         * @param elevation Elevation of the toast.
         * @param textSize Text size of the toast message.
         * @param typeface Typeface of the toast message.
         * @param gravity Gravity of the toast.
         * @param xOffset X axis offset for toast gravity.
         * @param yOffset Y axis offset for toast gravity.
         * @param duration Duration of the toast message.
         * @return Toast.
         */
        fun customAll(message: String, icon: Int, cardBackgroundColor: Int,
                      cornerRadius: Float, elevation: Float,
                      textSize: Float, typeface: Typeface, gravity: Int,
                      xOffset: Int, yOffset: Int, duration: Int): Toast {
            val toast = ToastHelper()
                .setIcon(icon)
                .setMessage(message)
                .setCardBackgroundColor(cardBackgroundColor)
                .setCardElevation(elevation)
                .setCardRadius(cornerRadius)
                .setTextSize(textSize)
                .setTypeFace(typeface)
                .createToast(duration, false)
            if (gravity != -1) toast.setGravity(gravity, xOffset, yOffset)
            return toast
        }

        /**
         * Simple way to create ToastieActivity. You can use it to fully customize toast.
         * @return ToastieActivity object
         */
        fun makeCustom(): ToastHelper {
            return ToastHelper()
        }

    }

}
