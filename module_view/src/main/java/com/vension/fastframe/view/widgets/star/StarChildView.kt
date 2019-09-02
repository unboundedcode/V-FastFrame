package com.vension.fastframe.view.widgets.star

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.vension.fastframe.view.R
import com.vension.fastframe.view.bean.HomePlanetBean
import kotlinx.android.synthetic.main.layout_star_child.view.*
import org.jetbrains.anko.toast

/**
 * ========================================================================
 * @author: Created by Vension on 2019/9/2 12:26.
 * @email : vensionHu@qq.com
 * @Github: https://github.com/Vension
 * __      ________ _   _  _____ _____ ____  _   _
 * \ \    / /  ____| \ | |/ ____|_   _/ __ \| \ | |
 *  \ \  / /| |__  |  \| | (___   | || |  | |  \| |
 *   \ \/ / |  __| | . ` |\___ \  | || |  | | . ` |
 *    \  /  | |____| |\  |____) |_| || |__| | |\  |
 *     \/   |______|_| \_|_____/|_____\____/|_| \_|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * @desc: happy code ->
 * ========================================================================
 */
class StarChildView(planetBean: HomePlanetBean, context: Context) : ConstraintLayout(context) {

    init {
        setWillNotDraw(false)
        LayoutInflater.from(context).inflate(R.layout.layout_star_child, this)
        button.setOnClickListener {
            context.toast("Button clicked")
        }

        planetBean.let {
            button.text = it.planetName
            if (it.isActivated) {
                appCompatImageView.setImageResource(it.planetActivateImage)
                textView.text = String.format("%d/%d",planetBean.needStars,planetBean.needStars)
                progressBar.progress = 100
            } else {
                appCompatImageView.setImageResource(it.planetNormalImage)
                progressBar.progress = 0
                textView.text = String.format("%d/%d",0,planetBean.needStars)
            }

            if (it.isActivated || it.canActivate) {
                button.setBackgroundResource(R.drawable.bg_award_exchange_enable)
                button.isEnabled = true
            } else {
                button.setBackgroundResource(R.drawable.bg_award_exchange_disenable)
                button.isEnabled = false
            }

            button.setOnClickListener {
                context.toast(planetBean.planetName)
            }
        }
    }
}
