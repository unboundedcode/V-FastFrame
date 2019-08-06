package kv.vension.fastframe.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import kv.vension.vframe.R

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/10/31 10:23
 * 描  述：多种状态Layout
 * ========================================================
 */

class MultiStateLayout : FrameLayout{

    var animDuration = 250L

    /**
     * 默认的 LayoutParams
     **/
    private val DEFAULT_LAYOUT_PARAMS = LayoutParams(
        LayoutParams.MATCH_PARENT,
        LayoutParams.MATCH_PARENT
    )

    val STATUS_CONTENT    = 0x00  //显示内容
    val STATUS_LOADING    = 0x01  //加载中...
    val STATUS_EMPTY      = 0x02  //空数据状态
    val STATUS_ERROR      = 0x03  //加载出错状态
    val STATUS_NO_NETWORK = 0x04  //网络连接问题


    private val NULL_RESOURCE_ID = -1  //默认内容id
    private var mLoadingViewResId: Int = 0
    private var mEmptyViewResId: Int = 0
    private var mErrorViewResId: Int = 0
    private var mNoNetworkViewResId: Int = 0
    private var mContentViewResId: Int = 0


    private var mLoadingView: View? = null
    private var mEmptyView: View? = null
    private var mErrorView: View? = null
    private var mNoNetworkView: View? = null
    private var mContentView: View? = null

    private var mViewStatus: Int = STATUS_LOADING

    private val mInflater: LayoutInflater by lazy {
        LayoutInflater.from(context)
    }

    private var mOnRetryClickListener: View.OnClickListener? = null
    private val mOtherIds = ArrayList<Int>()

    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        //取xml文件中设定的参数
        val ta = context.obtainStyledAttributes(attrs, R.styleable.MultiStateLayout, defStyleAttr, 0)
        mLoadingViewResId = ta.getResourceId(R.styleable.MultiStateLayout_mul_loadingView, R.layout.layout_mul_default_loading)
        mEmptyViewResId = ta.getResourceId(R.styleable.MultiStateLayout_mul_emptyView, R.layout.layout_mul_default_empty)
        mErrorViewResId = ta.getResourceId(R.styleable.MultiStateLayout_mul_errorView, R.layout.layout_mul_default_error)
        mNoNetworkViewResId = ta.getResourceId(R.styleable.MultiStateLayout_mul_noNetworkView, R.layout.layout_mul_default_network)
        mContentViewResId = ta.getResourceId(R.styleable.MultiStateLayout_mul_contentView, NULL_RESOURCE_ID)
        ta.recycle()
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        //此方法在xml加载完成后执行
        if (childCount == 0) {
            throw RuntimeException("content view can not be null")
        }
        if (childCount > 1) {
            removeViews(1, childCount - 1)
        }
        val view = getChildAt(0)
        mContentViewResId = view.id
        mContentView = view
        showContent()//默认显示内容布局
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clear(mEmptyView, mLoadingView, mErrorView, mNoNetworkView)
        if (!mOtherIds.isNullOrEmpty()){
            mOtherIds.clear()
        }
        if (null != mOnRetryClickListener) {
            mOnRetryClickListener = null
        }
    }

    /**
     * 获取当前状态
     */
    fun getViewStatus(): Int {
        return mViewStatus
    }

    /**
     * 设置重试点击事件
     *
     * @param onRetryClickListener 重试点击事件
     */
    fun setOnRetryClickListener(onRetryClickListener: View.OnClickListener) {
        this.mOnRetryClickListener = onRetryClickListener
    }

    /**
     * 显示空视图
     */
    fun showEmpty() {
        showEmpty(mEmptyViewResId, DEFAULT_LAYOUT_PARAMS)
    }

    /**
     * 显示空视图
     * @param layoutId 自定义布局文件
     * @param layoutParams 布局参数
     */
    fun showEmpty(layoutId: Int, layoutParams: ViewGroup.LayoutParams) {
        showEmpty(inflateView(layoutId),layoutParams)
    }

    /**
     * 显示空视图
     * @param view 自定义视图
     * @param layoutParams 布局参数
     */
    fun showEmpty(view: View, layoutParams: ViewGroup.LayoutParams) {
        checkNull(view, "EmptyView is null!")
        checkNull(layoutParams, "LayoutParams is null!")
        mViewStatus = STATUS_EMPTY
        if (null == mEmptyView) {
            mEmptyView = view
            val emptyRetryView = mEmptyView?.findViewById<View>(R.id.empty_retry_view)
            if (null != mOnRetryClickListener && null != emptyRetryView) {
                emptyRetryView.setOnClickListener(mOnRetryClickListener)
            }
            mOtherIds.add(mEmptyView!!.id)
            addView(mEmptyView, 0, layoutParams)
        }
        showViewById(mEmptyView!!.id)
    }

    /**
     * 显示错误视图
     */
    fun showError() {
        showError(mErrorViewResId, DEFAULT_LAYOUT_PARAMS)
    }

    /**
     * 显示错误视图
     * @param layoutId 自定义布局文件
     * @param layoutParams 布局参数
     */
    fun showError(layoutId: Int, layoutParams: ViewGroup.LayoutParams) {
        showError(inflateView(layoutId),layoutParams)
    }

    /**
     * 显示错误视图
     * @param view 自定义视图
     * @param layoutParams 布局参数
     */
    fun showError(view: View, layoutParams: ViewGroup.LayoutParams) {
        checkNull(view, "Error view is null!")
        checkNull(layoutParams, "LayoutParams is null!")
        mViewStatus = STATUS_ERROR
        if (null == mErrorView) {
            mErrorView = view
            val errorRetryView = mErrorView?.findViewById<View>(R.id.error_retry_view)
            if (null != mOnRetryClickListener && null != errorRetryView) {
                errorRetryView.setOnClickListener(mOnRetryClickListener)
            }
            mOtherIds.add(mErrorView!!.id)
            addView(mErrorView, 0, layoutParams)
        }
        showViewById(mErrorView!!.id)
    }

    /**
     * 显示加载中视图
     */
    fun showLoading() {
        showLoading(mLoadingViewResId, DEFAULT_LAYOUT_PARAMS)
    }


    /**
     * 显示加载中视图
     * @param layoutId 自定义布局文件
     * @param layoutParams 布局参数
     */
    fun showLoading(layoutId: Int, layoutParams: ViewGroup.LayoutParams) {
        showLoading(inflateView(layoutId),layoutParams)
    }

    /**
     * 显示加载中视图
     * @param view 自定义视图
     * @param layoutParams 布局参数
     */
    fun showLoading(view: View, layoutParams: ViewGroup.LayoutParams) {
        checkNull(view, "Loading view is null!")
        checkNull(layoutParams, "LayoutParams is null!")
        mViewStatus = STATUS_LOADING
        if (null == mLoadingView) {
            mLoadingView = view
            mOtherIds.add(mLoadingView!!.id)
            addView(mLoadingView, 0, layoutParams)
        }
        showViewById(mLoadingView!!.id)
    }

    /**
     * 显示无网络视图
     */
    fun showNoNetwork() {
        showNoNetwork(mNoNetworkViewResId, DEFAULT_LAYOUT_PARAMS)
    }

    /**
     * 显示无网络视图
     * @param layoutId 自定义布局文件
     * @param layoutParams 布局参数
     */
    fun showNoNetwork(layoutId: Int, layoutParams: ViewGroup.LayoutParams) {
        showNoNetwork(inflateView(layoutId), layoutParams)
    }

    /**
     * 显示无网络视图
     * @param view 自定义视图
     * @param layoutParams 布局参数
     */
    fun showNoNetwork(view: View, layoutParams: ViewGroup.LayoutParams) {
        checkNull(view, "No network view is null!")
        checkNull(layoutParams, "LayoutParams is null!")
        mViewStatus = STATUS_NO_NETWORK
        if (null == mNoNetworkView) {
            mNoNetworkView = view
            val noNetworkRetryView = mNoNetworkView?.findViewById<View>(R.id.no_network_retry_view)
            if (null != mOnRetryClickListener && null != noNetworkRetryView) {
                noNetworkRetryView.setOnClickListener(mOnRetryClickListener)
            }
            mOtherIds.add(mNoNetworkView!!.id)
            addView(mNoNetworkView, 0, layoutParams)
        }
        showViewById(mNoNetworkView!!.id)
    }


    /**
     * 显示内容视图
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    fun showContent(layoutId: Int, layoutParams: ViewGroup.LayoutParams) {
        showContent(inflateView(layoutId), layoutParams)
    }

    /**
     * 显示内容视图
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    fun showContent(view: View, layoutParams: ViewGroup.LayoutParams) {
        checkNull(view, "Content view is null.")
        checkNull(layoutParams, "Layout params is null.")
        mViewStatus = STATUS_CONTENT
        clear(mContentView)
        mContentView = view
        addView(mContentView, 0, layoutParams)
        showViewById(mContentView!!.id)
    }

    /**
     * 显示内容视图
     */
    fun showContent() {
        mViewStatus = STATUS_CONTENT
        if (null == mContentView && mContentViewResId != NULL_RESOURCE_ID) {
            mContentView = mInflater!!.inflate(mContentViewResId, null)
            addView(mContentView, 0, DEFAULT_LAYOUT_PARAMS)
        }
        showContentView()
    }

    private fun showContentView() {
        val childCount = childCount
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            view.visibility = if (mOtherIds.contains(view.id)) View.GONE else View.VISIBLE
        }
    }


    private fun inflateView(layoutId: Int): View {
        return mInflater.inflate(layoutId, null)
    }

    private fun showViewById(viewId: Int) {
        val childCount = childCount
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            view.visibility = if (view.id == viewId) View.VISIBLE else View.GONE
        }
    }

    private fun checkNull(key : Any?, hint: String) {
        if (null == key) {
            throw NullPointerException(hint)
        }
    }

    private fun clear(vararg views: View?) {
        if (views.isNullOrEmpty()) {
            return
        }
        try {
            for (view in views) {
                if (null != view) {
                    removeView(view)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}