package kv.vension.fastframe.views.helper

import android.os.Build
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayout
import kv.vension.fastframe.VFrame
import kv.vension.fastframe.utils.DensityUtil
import java.lang.reflect.Field

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/10/30 18:13
 * 描  述：代码修改TabLayout下划线的LayoutParams
 * ========================================================
 */

object TabLayoutHelper{

    fun setUpIndicatorWidth(tabLayout: TabLayout) {
        val tabLayoutClass = tabLayout.javaClass
        var tabStrip: Field? = null
        try {
            tabStrip = tabLayoutClass.getDeclaredField("mTabStrip")
            tabStrip.isAccessible = true
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }

        var layout: LinearLayout? = null
        try {
            if (tabStrip != null) {
                layout = tabStrip.get(tabLayout) as LinearLayout
            }
            for (i in 0 until layout!!.childCount) {
                val child = layout.getChildAt(i)
                child.setPadding(0, 0, 0, 0)
                val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    params.marginStart = DensityUtil.dp2px(VFrame.getContext(), 50f)
                    params.marginEnd = DensityUtil.dp2px(VFrame.getContext(), 50f)
                }
                child.layoutParams = params
                child.invalidate()
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

}