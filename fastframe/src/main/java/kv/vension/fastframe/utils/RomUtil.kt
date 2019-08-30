package kv.vension.fastframe.utils

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import java.io.*
import java.util.*


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/21 12:18
 * 描  述：系统操作相关
 * ========================================================
 */

object RomUtil {

    internal object AvailableRomType {
        val MIUI = 1
        val FLYME = 2
        val ANDROID_NATIVE = 3
        val NA = 4
    }

    fun isLightStatusBarAvailable(): Boolean {
        return isMIUIV6OrAbove() || isFlymeV4OrAbove() || isAndroidMOrAbove()
    }

    fun getLightStatausBarAvailableRomType(): Int {
        if (isMIUIV6OrAbove()) {
            return RomUtil.AvailableRomType.MIUI
        }

        if (isFlymeV4OrAbove()) {
            return RomUtil.AvailableRomType.FLYME
        }

        return if (isAndroidMOrAbove()) {
            RomUtil.AvailableRomType.ANDROID_NATIVE
        } else RomUtil.AvailableRomType.NA

    }

    //Flyme V4的displayId格式为 [Flyme OS 4.x.x.xA]
    //Flyme V5的displayId格式为 [Flyme 5.x.x.x beta]
    private fun isFlymeV4OrAbove(): Boolean {
        val displayId = Build.DISPLAY
        if (!TextUtils.isEmpty(displayId) && displayId.contains("Flyme")) {
            val displayIdArray = displayId.split(" ".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            for (temp in displayIdArray) {
                //版本号4以上，形如4.x.
                if (temp.matches("^[4-9]\\.(\\d+\\.)+\\S*".toRegex())) {
                    return true
                }
            }
        }
        return false
    }

    //MIUI V6对应的versionCode是4
    //MIUI V7对应的versionCode是5
    private fun isMIUIV6OrAbove(): Boolean {
        val miuiVersionCodeStr = getSystemProperty("ro.miui.ui.version.code")
        if (!TextUtils.isEmpty(miuiVersionCodeStr)) {
            try {
                val miuiVersionCode = Integer.parseInt(miuiVersionCodeStr.toString())
                if (miuiVersionCode >= 4) {
                    return true
                }
            } catch (e: Exception) {
            }

        }
        return false
    }

    //Android Api 23以上
    private fun isAndroidMOrAbove(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    private fun getSystemProperty(propName: String): String? {
        val line: String
        var input: BufferedReader? = null
        try {
            val p = Runtime.getRuntime().exec("getprop " + propName)
            input = BufferedReader(InputStreamReader(p.inputStream), 1024)
            line = input!!.readLine()
            input!!.close()
        } catch (ex: IOException) {
            return null
        } finally {
            if (input != null) {
                try {
                    input!!.close()
                } catch (e: IOException) {
                }

            }
        }
        return line
    }


    val REQ_CODE_PERMISSION = 123

    private val KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level"
    private val KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion"
    private val KEY_EMUI_VERSION = "ro.build.version.emui"
    private val KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code"
    private val KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name"
    private val KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage"


    /**
     * 是否为华为手机
     *
     * @return
     */
    fun isEMUI(): Boolean {
        try {
            return getProperty(KEY_EMUI_API_LEVEL, null) != null || getProperty(
                KEY_EMUI_CONFIG_HW_SYS_VERSION,
                null
            ) != null || getProperty(KEY_EMUI_VERSION, null) != null
        } catch (e: IOException) {
            return false
        }

    }

    /**
     * 是否为小米手机
     *
     * @return
     */
    fun isMIUI(): Boolean {
        try {
            return getProperty(KEY_MIUI_VERSION_CODE, null) != null || getProperty(
                KEY_MIUI_VERSION_NAME,
                null
            ) != null || getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null
        } catch (e: IOException) {
            return false
        }

    }

    /**
     * 是否为魅族手机
     *
     * @return
     */
    fun isFlyme(): Boolean {
        try {
            val method = Build::class.java.getMethod("hasSmartBar")
            return method != null
        } catch (e: Exception) {
            return false
        }

    }


    @Throws(IOException::class)
    private fun getProperty(name: String, defaultValue: String?): String? {
        //Android 8.0以下可通过访问build.prop文件获取相关属性，8.0及以上无法访问，需采用反射获取
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val properties = Properties()
            properties.load(FileInputStream(File(Environment.getRootDirectory(), "build.prop")))
            return properties.getProperty(name, defaultValue)
        } else {
            try {
                val clz = Class.forName("android.os.SystemProperties")
                val get = clz.getMethod("get", String::class.java, String::class.java)
                val property = get.invoke(clz, name, defaultValue) as String
                return if (TextUtils.isEmpty(property))
                    null
                else
                    property
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return defaultValue
        }
    }

    //跳转到权限管理页面，兼容不同手机系统类型
    fun goPermissionPage(context: Activity) {
        if (isFlyme()) {
            val intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.putExtra("packageName", context.packageName)
            try {
                context.startActivityForResult(intent, REQ_CODE_PERMISSION)
            } catch (e: Exception) {
                e.printStackTrace()
                context.startActivityForResult(
                    getAppDetailSettingIntent(context),
                    REQ_CODE_PERMISSION
                )
            }

        } else if (isMIUI()) {
            try {
                // 高版本MIUI 访问的是PermissionsEditorActivity，如果不存在再去访问AppPermissionsEditorActivity
                val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
                val componentName = ComponentName(
                    "com.miui.securitycenter",
                    "com.miui.permcenter.permissions.PermissionsEditorActivity"
                )
                intent.component = componentName
                intent.putExtra("extra_pkgname", context.packageName)
                context.startActivityForResult(intent, REQ_CODE_PERMISSION)
            } catch (e: Exception) {
                try {
                    // 低版本MIUI
                    val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
                    val componentName = ComponentName(
                        "com.miui.securitycenter",
                        "com.miui.permcenter.permissions.AppPermissionsEditorActivity"
                    )
                    intent.component = componentName
                    intent.putExtra("extra_pkgname", context.packageName)
                    context.startActivityForResult(intent, REQ_CODE_PERMISSION)
                } catch (e1: Exception) {
                    e1.printStackTrace()
                    context.startActivityForResult(
                        getAppDetailSettingIntent(context),
                        REQ_CODE_PERMISSION
                    )
                }

            }

        } else if (isEMUI()) {
            val intent = Intent()
            //                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            val comp = ComponentName(
                "com.huawei.systemmanager",
                "com.huawei.permissionmanager.ui.MainActivity"
            )//华为权限管理
            intent.component = comp
            try {
                context.startActivityForResult(intent, REQ_CODE_PERMISSION)
            } catch (e: Exception) {
                e.printStackTrace()
                context.startActivityForResult(
                    getAppDetailSettingIntent(context),
                    REQ_CODE_PERMISSION
                )
            }

        } else {
            context.startActivityForResult(getAppDetailSettingIntent(context), REQ_CODE_PERMISSION)
        }
    }

    /**
     * 获取应用详情页面intent
     *
     * @return
     */
    fun getAppDetailSettingIntent(context: Context): Intent {
        val localIntent = Intent()
        //        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            localIntent.data = Uri.fromParts("package", context.packageName, null)
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.action = Intent.ACTION_VIEW
            localIntent.setClassName(
                "com.android.settings",
                "com.android.settings.InstalledAppDetails"
            )
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.packageName)
        }
        return localIntent
    }

}