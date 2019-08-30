package kv.vension.fastframe.image.glide

import android.content.Context
import androidx.annotation.NonNull
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder

/**
 * ========================================================================
 * @author: Created by Vension on 2019/8/28 12:10.
 * @email:  vensionHu@qq.com
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
 * @desc: 如果你想具有配置 @{@link Glide} 的权利,则需要让 {@link ILoaderStrategy}
 * 的实现类也必须实现 {@link GlideAppOptions}
 * ========================================================================
 */
interface GlideAppOptions {

    /**
     * 配置 @[Glide] 的自定义参数,此方法在 @[Glide] 初始化时执行(@[Glide] 在第一次被调用时初始化),只会执行一次
     *
     * @param context
     * @param builder [GlideBuilder] 此类被用来创建 Glide
     */
     fun applyGlideOptions(@NonNull context: Context, @NonNull builder: GlideBuilder)

}