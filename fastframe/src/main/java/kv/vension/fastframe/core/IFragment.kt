package kv.vension.fastframe.core

import android.view.KeyEvent
import android.view.View
import androidx.annotation.IdRes

/**
 * ========================================================================
 * 作 者：Vension
 * 主 页：Github: https://github.com/Vension
 * 日 期：2019/7/15 12:13
 *   ______       _____ _______ /\/|_____ _____            __  __ ______
 *  |  ____/\    / ____|__   __|/\/  ____|  __ \     /\   |  \/  |  ____|
 *  | |__ /  \  | (___    | |     | |__  | |__) |   /  \  | \  / | |__
 *  |  __/ /\ \  \___ \   | |     |  __| |  _  /   / /\ \ | |\/| |  __|
 *  | | / ____ \ ____) |  | |     | |    | | \ \  / ____ \| |  | | |____
 *  |_|/_/    \_\_____/   |_|     |_|    |_|  \_\/_/    \_\_|  |_|______|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * 描述：AbsCompatFragment - 接口
 * ========================================================================
 */
interface IFragment : IAppCompatPage {

    fun <V : View> findViewById(@IdRes id: Int): V

    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean

    fun onBackPressed(): Boolean

}