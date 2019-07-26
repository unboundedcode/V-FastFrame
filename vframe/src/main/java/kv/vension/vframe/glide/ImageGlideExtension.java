//package frame.vension.kotlin.glide;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import com.bumptech.glide.Priority;
//import com.bumptech.glide.annotation.GlideExtension;
//import com.bumptech.glide.annotation.GlideOption;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
//import com.bumptech.glide.request.RequestOptions;
//import frame.vension.kotlin.R;
//import frame.vension.kotlin.glide.transformation.BlurBitmapTransformation;
//import frame.vension.kotlin.glide.transformation.CircleBitmapTransformation;
//import frame.vension.kotlin.glide.transformation.RoundBitmapTransformation;
//
///**
// * ========================================================
// * 作  者：Vension
// * 日  期：2018/9/27 15:21
// * 描  述：
// * ========================================================
// */
//
//@GlideExtension
//@SuppressLint("CheckResult")
//public class ImageGlideExtension {
//
//	public static final int MINI_THUMB_SIZE = 100;
//
//	private ImageGlideExtension() {
//
//	}
//
//	@GlideOption
//	public static void miniThumb(RequestOptions options) {
//		// miniThumb 缩略图最小大小
//		options.
//				fitCenter()
//				.override(MINI_THUMB_SIZE);
//	}
//
//	@GlideOption
//	public static void options(RequestOptions options) {
//		options
////				.signature(new ObjectKey())// 定制缓存刷新策略
////				.error(R.drawable.img_load_default)// error 请求图片加载错误的占位符
////				.error(new ColorDrawable(Color.RED))
////				.fallback(R.drawable.img_load_default)// Fallback 请求url/model为空的占位符
//				.placeholder(R.color.placeholder_color)// placeholder 请求图片加载中的占位符
////				.placeholder(new ColorDrawable(Color.GRAY))
//				.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)// 磁盘缓存策略
//				.priority(Priority.HIGH)// 高清图片
////				.onlyRetrieveFromCache(true)// 仅从缓存加载图片(比如省流量模式)
////				.skipMemoryCache(true)// 跳过缓存(比如图片验证码)
//				.timeout(60_000)// 60秒请求超时
////				.centerCrop()// 中间截取图片
////				.dontAnimate()// 不启用动画
////				.dontTransform()
//		;
//	}
//
//	@GlideOption
//	public static void roundCrop(RequestOptions options, int radius) {
//		options.downsample(DownsampleStrategy.CENTER_INSIDE)
//				.transform(new RoundBitmapTransformation(radius));
//	}
//
//	@GlideOption
//	public static void circleCrop(RequestOptions options) {
//		options.downsample(DownsampleStrategy.CENTER_INSIDE)
//				.transform(new CircleBitmapTransformation());
//	}
//
//	@GlideOption
//	public static void blurCrop(RequestOptions options, Context context, int blur) {
//		options.downsample(DownsampleStrategy.CENTER_INSIDE)
//				.transform(new BlurBitmapTransformation(context, blur));
//	}
//
//}
