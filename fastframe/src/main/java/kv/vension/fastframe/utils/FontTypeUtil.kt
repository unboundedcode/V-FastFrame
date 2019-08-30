package kv.vension.fastframe.utils

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * ========================================================================
 * 作 者：Vension
 * 主 页：Github: https://github.com/Vension
 * 日 期：2019/8/23 15:43
 * ______       _____ _______ /\/|_____ _____            __  __ ______
 * |  ____/\    / ____|__   __|/\/  ____|  __ \     /\   |  \/  |  ____|
 * | |__ /  \  | (___    | |     | |__  | |__) |   /  \  | \  / | |__
 * |  __/ /\ \  \___ \   | |     |  __| |  _  /   / /\ \ | |\/| |  __|
 * | | / ____ \ ____) |  | |     | |    | | \ \  / ____ \| |  | | |____
 * |_|/_/    \_\_____/   |_|     |_|    |_|  \_\/_/    \_\_|  |_|______|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * 描述：全局修改字体样式的工具类
 * ========================================================================
 */
object FontTypeUtil {

    //------------------ 在Activity/Fragment的基类中调用即可 -------------------//
    /**
     *
     * Replace the font of specified view and it's children
     * 通过递归批量替换某个View及其子View的字体改变Activity内部控件的字体(TextView,Button,EditText,CheckBox,RadioButton等)
     * @param context
     * @param fontPath  assert文件夹下的字体文件路径，如 "fonts/FZLanTYJW.ttf"
     */
    fun replaceFont(context: Activity, fontPath: String) {
        replaceFont(getRootView(context), fontPath)
    }


    /**
     *
     * Replace the font of specified view and it's children
     * @param root The root view.
     * @param fontPath font file path relative to 'assets' directory.
     */
    fun replaceFont(root: View, fontPath: String) {
        if (root == null || TextUtils.isEmpty(fontPath)) {
            return
        }

        if (root is TextView) { // If view is TextView or it's subclass, replace it's font
            var style = Typeface.NORMAL
            if (root.typeface != null) {
                style = root.typeface.style
            }
            root.setTypeface(createTypeface(root.getContext(), fontPath), style)
        } else if (root is ViewGroup) { // If view is ViewGroup, apply this method on it's child views
            for (i in 0 until root.childCount) {
                replaceFont(root.getChildAt(i), fontPath)
            }
        }
    }

    /*
     * Create a Typeface instance with your font file
     */
    fun createTypeface(context: Context, fontPath: String): Typeface {
        return Typeface.createFromAsset(context.assets, fontPath)
    }

    /**
     * 从Activity 获取 rootView 根节点
     * @param context
     * @return 当前activity布局的根节点
     */
    fun getRootView(context: Activity): View {
        return (context.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)
    }


    //------------------ 在Application中调用即可 -------------------//
    /**
     * 通过改变App的系统字体替换App内部所有控件的字体(TextView,Button,EditText,CheckBox,RadioButton等)
     * @param context
     * @param fontPath assert文件夹下的字体文件路径，如 "fonts/FZLanTYJW.ttf"
     */
    fun replaceSystemDefaultFont(context: Context, fontPath: String) {
        replaceTypefaceField("MONOSPACE", createTypeface(context, fontPath))
    }

    /**
     *
     * Replace field in class Typeface with reflection.
     */
    private fun replaceTypefaceField(fieldName: String, value: Any) {
        try {
            val defaultField = Typeface::class.java.getDeclaredField(fieldName)
            defaultField.isAccessible = true
            defaultField.set(null, value)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }

}