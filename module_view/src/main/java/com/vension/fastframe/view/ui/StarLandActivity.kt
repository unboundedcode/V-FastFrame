package com.vension.fastframe.view.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.vension.fastframe.view.R
import com.vension.fastframe.view.bean.HomePlanetBean
import com.vension.fastframe.view.widgets.star.StarChildView
import com.vension.fastframe.view.widgets.star.StarGroupView
import java.util.*

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
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class StarLandActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isDemo = intent.getBooleanExtra("isDemo", true)
        if (isDemo) {
            setContentView(R.layout.activity_land)
            val sunView = findViewById<View>(R.id.sun_view)
            val tv1 = findViewById<View>(R.id.tv1)
            sunView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.sun_anim))
            tv1.setOnClickListener {
                Toast.makeText(
                    this@StarLandActivity,
                    "clieck tv1",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            setContentView(R.layout.activity_land_star)
            initData()
        }
    }


    private fun initData() {
        val result = ArrayList<HomePlanetBean>()
        val moon = HomePlanetBean("月球", R.drawable.planet_yueqiu_mormal, R.drawable.planet_yueqiu_activated,
            500, true, true
        )
        val shui = HomePlanetBean(
            "水星", R.drawable.planet_shuixing_normal, R.drawable.planet_shuixing_activated,
            1000, true, true
        )
        val jin = HomePlanetBean(
            "金星", R.drawable.planet_jinxing_normal, R.drawable.planet_jinxing_activated,
            1500, true, true
        )
        val earth = HomePlanetBean(
            "地球", R.drawable.planet_diqiu_normal, R.drawable.planet_diqiu_activated,
            2000, true, true
        )
        val fire = HomePlanetBean(
            "火星", R.drawable.planet_huoxing_normal, R.drawable.planet_huoxing_activated,
            2500, true, true
        )
        val wood = HomePlanetBean(
            "木星", R.drawable.planet_muxing_normal, R.drawable.planet_muxing_activated,
            3000, false, true
        )
        val soil = HomePlanetBean(
            "土星", R.drawable.planet_tuxing_normal, R.drawable.planet_tuxing_activated,
            3500, true, true
        )
        val gold = HomePlanetBean(
            "天王星", R.drawable.planet_tianwangxing_normal, R.drawable.planet_tianwangxing_activated,
            4000, true, true
        )
        val ocean = HomePlanetBean(
            "海王星", R.drawable.planet_haiwangxing_normal, R.drawable.planet_haiwagnxing_activated,
            4500, false, false
        )
        result.add(moon)
        result.add(shui)
        result.add(jin)
        result.add(earth)
        result.add(fire)
        result.add(wood)
        result.add(soil)
        result.add(gold)
        result.add(ocean)

        val starGroupView = findViewById<StarGroupView>(R.id.starGroupView)
        for (i in result.indices) {
            val child = StarChildView(result[i], this)
            starGroupView.addView(child, FrameLayout.LayoutParams(-2, -2))
        }
        starGroupView.requestLayout()
        starGroupView.start()
    }
}
