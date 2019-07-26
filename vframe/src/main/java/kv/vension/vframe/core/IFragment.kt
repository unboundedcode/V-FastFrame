package kv.vension.vframe.core

import android.view.KeyEvent
import android.view.View
import androidx.annotation.IdRes

/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/15 12:13
 * 更 新：2019/7/15 12:13
 * 描 述： * 描  述：AbsCompatFragment - 接口

 * ========================================================
 */

interface IFragment : IAppCompatPage {

    fun <V : View> findViewById(@IdRes id: Int): V

    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean

    fun onBackPressed(): Boolean

}