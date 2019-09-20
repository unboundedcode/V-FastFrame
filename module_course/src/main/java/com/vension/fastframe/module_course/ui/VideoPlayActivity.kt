package com.vension.fastframe.module_course.ui

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.media.AudioManager
import android.net.Uri
import android.os.Bundle
import android.view.GestureDetector
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.vension.fastframe.module_course.R
import com.vension.fastframe.module_course.widget.media.MediaController
import com.vension.fastframe.module_course.widget.media.VideoPlayerView
import com.vension.fastframe.module_course.widget.media.callback.VideoBackListener
import kotlinx.android.synthetic.main.activity_wk_videp_play.*
import kv.vension.fastframe.core.AbsCompatActivity
import lib.vension.fastframe.common.router.RouterConfig
import tv.danmaku.ijk.media.player.IMediaPlayer

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/25 16:42.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/25 16:42
 * @desc: 视频播放activity
 * ===================================================================
 */

@Route(path = RouterConfig.PATH_COURSE_VIDEOPLAYACTIVITY)
class VideoPlayActivity : AbsCompatActivity(), VideoBackListener {

    private var mStartText = "初始化播放器..."
    private var mLoadingAnim: AnimationDrawable? = null
    private var mLastPosition = 0L
    //点击纵坐标L
    private var downX: Float = 0f
    //点击横坐标
    private var downY: Float = 0f
    //媒体音量管理
    private var audioManager: AudioManager? = null
    //屏幕当前亮度百分比
    private var currentF = 0f
    private var mUrl: String? = ""
    private var mTitle: String? = ""
    private var mGestureDetector: GestureDetector? = null
    private var mMediaController: MediaController? = null
    private var mMaxVolume: Int = 0


    override fun attachLayoutRes(): Int {
        return R.layout.activity_wk_videp_play
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        //初始化播放器
        initMediaPlayer()
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        mMaxVolume = audioManager!!.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val bundle = getBundleExtras()
        bundle?.let {
            mUrl = bundle.getString("Url")
            mTitle = bundle.getString("title")
        }

        initAnimation()
        player_view.setVideoURI(Uri.parse(mUrl))
        player_view.setOnPreparedListener {
            mLoadingAnim?.stop()
            mStartText = "$mStartText【完成】\n视频缓冲中..."
            tv_start?.text = mStartText
            rl_loading?.visibility = View.GONE
        }
        rl_start.visibility = View.GONE
    }

    private fun initMediaPlayer() {
        mMediaController = MediaController(this)
        mMediaController?.setTitle(mTitle)
        player_view.setMediaController(mMediaController)
        player_view.setMediaBufferingIndicator(rl_loading)
        player_view.requestFocus()
        player_view.setOnInfoListener(onInfoListener)
        player_view.setOnSeekCompleteListener(onSeekCompleteListener)
        player_view.setOnCompletionListener(onCompletionListener)
        player_view.setOnControllerEventsListener(onControllerEventsListener)
        //设置返回键监听
        mMediaController?.setVideoBackEvent(this)

    }

    /**
     * 初始化加载动画
     */
    private fun initAnimation() {
        rl_start.visibility = View.VISIBLE
        mStartText = "$mStartText【完成】\n解析视频地址...【完成】"
        tv_start.text = mStartText
        mLoadingAnim = iv_video_loading?.background as AnimationDrawable
        mLoadingAnim?.start()
    }


    /**
     * 视频缓冲事件回调
     */
    private val onInfoListener = IMediaPlayer.OnInfoListener { _, what, _ ->
        if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_END) {
            rl_loading?.visibility = View.GONE
        }
        true
    }

    /**
     * 视频跳转事件回调
     */
    private val onSeekCompleteListener = IMediaPlayer.OnSeekCompleteListener {
    }

    /**
     * 视频播放完成事件回调
     */
    private val onCompletionListener = IMediaPlayer.OnCompletionListener {
        player_view.pause()
    }


    /**
     * 控制条控制状态事件回调
     */
    private val onControllerEventsListener = object : VideoPlayerView.OnControllerEventsListener {
        override fun onVideoPause() {

        }

        override fun onVideoResume() {

        }
    }


    override fun onResume() {
        super.onResume()
        if (player_view != null && !player_view.isPlaying) {
            player_view?.seekTo(mLastPosition)
        }
    }

    override fun onPause() {
        super.onPause()
        player_view?.let {
            mLastPosition = player_view.currentPosition.toLong()
            player_view.pause()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mLoadingAnim?.let {
            mLoadingAnim?.stop()
            mLoadingAnim = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (player_view != null && player_view.isDrawingCacheEnabled) {
            player_view?.destroyDrawingCache()
        }
        mLoadingAnim?.let {
            mLoadingAnim?.stop()
            mLoadingAnim = null
        }
    }

    /**
     * 退出界面回调
     */
    override fun back() {
        onBackPressed()
    }

}