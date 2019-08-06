package lib.vension.fastframe.common.ui

import android.Manifest
import android.animation.*
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_splash.*
import kv.vension.fastframe.core.AbsCompatActivity
import kv.vension.fastframe.glide.ImageLoader
import kv.vension.fastframe.net.rx.RxHandler
import kv.vension.fastframe.utils.TimeUtil
import kv.vension.fastframe.views.CircleCountDownView
import lib.vension.fastframe.common.Constant
import lib.vension.fastframe.common.R
import lib.vension.fastframe.common.RouterConfig
import java.util.concurrent.TimeUnit


/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/17 17:48
 * 更 新：2019/7/17 17:48
 * 描 述：启动页
 * ========================================================
 */
@Route(path = RouterConfig.PATH_COMMON_SPLASHACTIVITY)
class SplashActivity : AbsCompatActivity() {

    private lateinit var animatorSet: AnimatorSet

    override fun isSupportSwipeBack(): Boolean {
        return false
    }

    override fun useEventBus(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.activity_splash
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        val welcomeHint = getString(R.string.welcome_hint,"2015", TimeUtil.getTime(System.currentTimeMillis(),"yyyy"), "V-FastFrame")
        tv_version.text = welcomeHint

        animatorSet = AnimatorSet().apply {
            ObjectAnimator.ofPropertyValuesHolder(//缩放回弹动画
                txt_des,
                PropertyValuesHolder.ofFloat("scaleX", 0f, 1.4f, 1.0f),
                PropertyValuesHolder.ofFloat("scaleY", 0f, 1.4f, 1.0f)
            ).apply {
                duration = 1000L
                interpolator = LinearInterpolator() as TimeInterpolator?
            }.let {
                play(it).with(//透明度渐变动画
                    ObjectAnimator.ofFloat(txt_des, "alpha", 1f).apply {
                        duration = 1000L
                        addListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationStart(animation: Animator) {
                                tv_name.visibility = View.INVISIBLE
                                circleCountDownView.visibility = View.INVISIBLE
                            }

                            override fun onAnimationEnd(animation: Animator) {
                                tv_name.visibility = View.VISIBLE
                            }
                        })
                    }
                )
                play(it).with(//往上向下掉回弹动画
                    ObjectAnimator.ofFloat(tv_name, "translationY", -500f, 0f).apply {
                        duration = 2000L
                        interpolator = BounceInterpolator()
                    }
                )
                play(it).before(//显示倒计时
                    ObjectAnimator.ofPropertyValuesHolder(//缩放回弹动画
                        circleCountDownView,
                        PropertyValuesHolder.ofFloat("scaleX", 0f, 1.4f, 1.0f),
                        PropertyValuesHolder.ofFloat("scaleY", 0f, 1.4f, 1.0f)
                    ).apply {
                        duration = 1000L
                        interpolator = LinearInterpolator()
                    }
                )
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {}
                @SuppressLint("CheckResult")
                override fun onAnimationEnd(animation: Animator?) {
                    Observable.timer(500, TimeUnit.MILLISECONDS).compose(RxHandler.ioToMain()).subscribe {
                        startLoadImg()//开始加载图片
                        startCountDown()//开始倒计时
                    }
                }
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
            })
            start()
        }
    }


    private fun startLoadImg(){
        txt_des.visibility = View.GONE
        tv_name.visibility = View.GONE
        circleCountDownView.visibility = View.VISIBLE
        aiv_splash.visibility = View.VISIBLE
        val splashImg = Constant.getSplashImg()
        ImageLoader.loadResource(this,splashImg,aiv_splash)
    }


    /**开始倒计时*/
    private val countDownTime = 4 * 1000
    private fun startCountDown() {
        circleCountDownView.setCountdownTime(countDownTime.toLong())
        circleCountDownView.startCountDownTime(object : CircleCountDownView.OnCountdownFinishListener{
            override fun countdownFinished() {
                checkPermission()
            }
        })
    }

    /**
     * 6.0以下版本(系统自动申请) 不会弹框
     * 有些厂商修改了6.0系统申请机制，他们修改成系统自动申请权限了
     */
    @SuppressLint("CheckResult")
    private fun checkPermission(){
        mRxPermissions?.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
            .subscribe { granted ->
                if (granted) {
                    // All requested permissions are granted
                    redirectToMain()
                } else {
                    // At least one permission is denied
                    Toast.makeText(this@SplashActivity,"用户关闭了权限", Toast.LENGTH_LONG).show()
                }
            }
    }

    /**进入主界面*/
    private fun redirectToMain() {
        ARouter.getInstance().build(RouterConfig.PATH_APP_MAINACTIVITY).navigation()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        animatorSet?.cancel()
        circleCountDownView.cancel()
    }


    //设置点击返回键不响应
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            true
        } else super.onKeyDown(keyCode, event)
    }

}