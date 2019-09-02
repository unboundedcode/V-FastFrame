package kv.vension.fastframe.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.tbruyelle.rxpermissions2.RxPermissions
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kv.vension.fastframe.VFrame
import kv.vension.fastframe.cache.PageCache
import kv.vension.fastframe.dialog.LoadingDialog
import kv.vension.fastframe.bus.event.BaseEvent
import kv.vension.fastframe.ext.Logi
import kv.vension.fastframe.utils.PreferenceUtil
import kv.vension.fastframe.views.MultiStateLayout
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * ========================================================================
 * 作 者：Vension
 * 主 页：Github: https://github.com/Vension
 * 日 期：2019/7/15 12:12
 *   ______       _____ _______ /\/|_____ _____            __  __ ______
 *  |  ____/\    / ____|__   __|/\/  ____|  __ \     /\   |  \/  |  ____|
 *  | |__ /  \  | (___    | |     | |__  | |__) |   /  \  | \  / | |__
 *  |  __/ /\ \  \___ \   | |     |  __| |  _  /   / /\ \ | |\/| |  __|
 *  | | / ____ \ ____) |  | |     | |    | | \ \  / ____ \| |  | | |____
 *  |_|/_/    \_\_____/   |_|     |_|    |_|  \_\/_/    \_\_|  |_|______|
 *
 * Take advantage of youth and toss about !
 * ------------------------------------------------------------------------
 * 描述：Fragment-基类
 *
 * 生命周期执行的方法 如下：
 * 第一次生成页面-->可见
 * setUserVisibleHint: ----->false
 * setUserVisibleHint: ----->true
 * onCreateView: -----> onCreateView
 * onStart: -----> onStart
 * onFragmentFirst: 首次可见
 * onFragmentFirst: -----> 子fragment进行初始化操作
 * onResume: -----> onResume
 *
 * 可见-->第一次隐藏：
 * onPause: -----> onPause
 * onFragmentInVisible: 不可见
 *
 * 未销毁且不可见-->重新可见：
 * onStart: -----> onStart
 * onFragmentVisble: 可见
 * onFragmentVisble: -----> 子fragment每次可见时的操作
 * onResume: -----> onResume
 *
 * 可见-->销毁：
 * onPause: -----> onPause
 * onFragmentInVisible: 不可见
 * onDestroyView: -----> onDestroyView
 *
 * 我们可以更具以上生命周期来操作不同的业务逻辑
 * ========================================================================
 */
abstract class AbsCompatFragment : Fragment(), IFragment,View.OnClickListener {

    val TAG = this.javaClass.simpleName //获取上下文并设置log标记

    /**
     * 在使用自定义toolbar时候的根布局 = toolBarView+childView
     */
    var rootView: View? = null
    /**
     * 多状态Layout MultiStateLayout
     */
    protected var fraMultiStateLayout: MultiStateLayout? = null
    /**
     * 通用标题栏 CommonTitleBar
     */
    protected lateinit var mCommonTitleBar: CommonTitleBar
    /**
     * 6.0动态获取权限
     */
    protected lateinit var mRxPermissions: RxPermissions
    /**
     * 缓存上一次的网络状态
     */
    private var hasNetwork: Boolean by PreferenceUtil("has_network", true)
    /**
     * LoadingDialog
     */
    protected val mLoadingDialog: LoadingDialog by lazy { LoadingDialog.Builder(context!!).setCancelable(false).setCancelOutside(false).create() }

    lateinit var mContext: Context
    lateinit var mActivity: Activity

    private var isPagePrepare = false//视图是否准备完毕
    private var mIsFirstVisible = true/*当前Fragment是否首次可见，默认是首次可见**/
    private var currentVisibleState = false/*当前Fragment的可见状态，一种当前可见，一种当前不可见**/

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as Activity
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null){
            //为空时初始化。
            if (showToolBar() && getToolBarResId() > 0) {
                //如果需要显示自定义toolbar,并且资源id存在的情况下，实例化baseView;
                rootView = LayoutInflater.from(context).inflate(
                    if (isToolbarCover())
                        kv.vension.fastframe.R.layout.activity_base_toolbar_cover
                    else
                        kv.vension.fastframe.R.layout.activity_base,
                    null,false)//根布局

                //toolbar容器
                val vsToolbar = rootView?.findViewById<View>(kv.vension.fastframe.R.id.vs_toolbar) as ViewStub
                //子布局容器
                val flContainer = rootView?.findViewById<View>(kv.vension.fastframe.R.id.fl_container) as FrameLayout
                vsToolbar.layoutResource = getToolBarResId()//toolbar资源id
                vsToolbar.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                vsToolbar.inflate()//显示toolbar
                inflater.inflate(attachLayoutRes(), flContainer, true)//子布局
            } else {
                //不显示通用toolbar
                rootView = inflater.inflate(attachLayoutRes(), container, false)
            }
        }
        return rootView
    }

    /**
     * 第一步,改变isPrepared标记
     * 当onViewCreated()方法执行时,表明View已经加载完毕,此时改变isPagePrepare标记为true,并调用lazyLoad()方法
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isPagePrepare = true //标记页面初始化完成
        if (showToolBar()){
            mCommonTitleBar = rootView?.findViewById<View>(kv.vension.fastframe.R.id.commonTitleBar) as CommonTitleBar//子布局容器
            initToolBar(mCommonTitleBar)//初始化标题栏
        }
        mRxPermissions = RxPermissions(this)

        //开启事件总线
        if (useEventBus()) {
            VFrame.busManager.register(this)
        }

        //初始化view和数据
        initViewAndData(view,savedInstanceState)
        //多种状态切换的view 重试点击事件
        fraMultiStateLayout?.let {
            it.setOnRetryClickListener(mRetryClickListener)
        }
        PageCache.pageFragmentCache.add(this)
    }


    override fun onClick(v: View?) {

    }


    override fun onStart() {
        super.onStart()
        //isHidden()是Fragment是否处于隐藏状态和isVisible()有区别
        //getUserVisibleHint(),Fragement是否可见
        if (!isHidden && userVisibleHint) {//如果Fragment没有隐藏且可见
            //执行分发的方法,三种结果对应自Fragment的三个回调，对应的操作，Fragment首次加载，可见，不可见
            disPatchFragment(true)
        }
    }

    override fun onResume() {
        super.onResume()
        if (!mIsFirstVisible) {
            //表示点击home键又返回操作,设置可见状态为ture
            if (!isHidden && !userVisibleHint && currentVisibleState) {
                disPatchFragment(true)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        //表示点击home键,原来可见的Fragment要走该方法，更改Fragment的状态为不可见
        if (!isHidden && userVisibleHint) {
            disPatchFragment(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //当 View 被销毁的时候我们需要重新设置 isViewCreated mIsFirstVisible 的状态
        if (useEventBus()) {
            VFrame.busManager.unRegister(this)
        }
        rootView = null
        if(mLoadingDialog.isShowing){
            mLoadingDialog.dismiss()
        }
        PageCache.pageFragmentCache.remove(this)//
    }

    //结合viewpager
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //如果fragment可见进行懒加载数据,setUserVisibleHint()有可能在fragment的生命周期外被调用
        //如果view还未初始化，不进行处理
        if(isPagePrepare){
            rootView?.let {
                //Fragment可见且状态不是可见(从一个Fragment切换到另外一个Fragment,后一个设置状态为可见)
                if (isVisibleToUser && !currentVisibleState) {
                    disPatchFragment(true)
                } else if (!isVisibleToUser && currentVisibleState) {
                    //Fragment不可见且状态是可见(从一个Fragment切换到另外一个Fragment,前一个更改状态为不可见)
                    disPatchFragment(false)
                }
            }
        }
    }


    /**
     * @param visible Fragment当前是否可见，然后调用相关方法
     */
    private fun disPatchFragment(visible: Boolean) {
        currentVisibleState = visible
        if (visible && isPagePrepare) {//Fragment可见
            if (mIsFirstVisible) {//可见又是第一次
                mIsFirstVisible = false//改变首次可见的状态
                onFragmentFirst()//页面对用户可见且初始化完成时进行懒加载数据
            } else {//可见但不是第一次
                onFragmentVisble()
            }
        } else {//不可见
            onFragmentInVisible()
        }
    }

    /**
     * Fragemnet首次可见的方法页面初始化完进行懒加载
     */
    //
    private fun onFragmentFirst() {
        Logi("$TAG--首次可见")
        lazyLoadData()
    }

    //Fragemnet可见的方法
    open fun onFragmentVisble() {//子Fragment调用此方法，执行可见操作
        Logi("$TAG--可见")
    }

    //Fragemnet不可见的方法
    open fun onFragmentInVisible() {
        Logi("$TAG--不可见")
    }



    /** ======================= 抽象方法 ============================= */

    /**
     *  加载布局
     */
    @LayoutRes
    abstract fun attachLayoutRes(): Int
    /**
     *  请求数据前的一些初始化设置
     */
    abstract fun initViewAndData(view: View,savedInstanceState: Bundle?)
    /**
     * 第一次可见时触发调用,此处实现具体的数据请求逻辑
     */
    abstract fun lazyLoadData()
    /**
     * 无网状态—>有网状态 的自动重连操作，子类可重写该方法
     */
    open fun doReConnected() {
        lazyLoadData()
    }

    private val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        lazyLoadData()
    }




    /**
     * 获取自定义toolbarview 资源id 默认为-1，
     * showToolBar()方法必须返回true才有效
     */
    private fun getToolBarResId(): Int {
        return getToolBarResId(0)
    }

    open fun getToolBarResId(layout : Int): Int {
        return if (layout > 0) layout else kv.vension.fastframe.R.layout.layout_default_toolbar
    }


    override fun <V : View> findViewById(id: Int): V {
        return rootView?.findViewById(id) as V
    }


    /**初始化标题栏*/
    open fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        initToolBar(mCommonTitleBar,"")
    }
    open fun initToolBar(mCommonTitleBar: CommonTitleBar,title:String) {
        mCommonTitleBar.apply {
            setListener {_: View?, action: Int, _: String? ->
                if (action == CommonTitleBar.ACTION_LEFT_BUTTON || action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    activity?.onBackPressed()
                }
            }
            centerTextView.text = title
        }
    }

    /**
     * 网络状态变化事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun <T> onEvent(event: BaseEvent<T>) {
        if(!hasNetwork){
            if (event.isNetConnected) {
                doReConnected()
            }
        }
        hasNetwork = event.isNetConnected
    }


    /** ====================== implements start ======================= */


    override fun getPageFragmentManager(): FragmentManager {
        return childFragmentManager
    }

    override fun getBundleExtras(): Bundle {
        return arguments ?: Bundle()
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return false
    }

    override fun postDelay(delayMillis: Long, block: () -> Unit) {
        activity?.window?.decorView?.postDelayed(block, delayMillis)
    }


    override fun startProxyActivity(_Class: Class<out Fragment>) {
        startProxyActivity(_Class,Bundle(),null)
    }

    override fun startProxyActivity(_Class: Class<out Fragment>, view: View?) {
        startProxyActivity(_Class,Bundle(),view)
    }

    override fun startProxyActivity(_Class: Class<out Fragment>, bundle: Bundle) {
        startProxyActivity(_Class,bundle,null)
    }

    override fun startProxyActivity(_Class: Class<out Fragment>, bundle: Bundle, view: View?) {
        startProxyActivityForResult(_Class, bundle, view, -1)
    }

    override fun startProxyActivityForResult(_Class: Class<out Fragment>, requestCode: Int) {
        startProxyActivityForResult(_Class, Bundle(),null,requestCode)
    }

    override fun startProxyActivityForResult(_Class: Class<out Fragment>, view: View?, requestCode: Int) {
        startProxyActivityForResult(_Class, Bundle(),view,requestCode)
    }

    override fun startProxyActivityForResult(_Class: Class<out Fragment>, bundle: Bundle, requestCode: Int) {
        startProxyActivityForResult(_Class,bundle,null,requestCode)
    }

    override fun startProxyActivityForResult(_Class: Class<out Fragment>, bundle: Bundle, view: View?, requestCode: Int) {
        var options: Bundle? = null
        if (null != view) {
            options = ActivityOptionsCompat.makeScaleUpAnimation(view, view.width / 2, view.height / 2, 0, 0).toBundle()
        }
        val mIntent = Intent(activity, ProxyActivity::class.java)
        bundle?.let {
            it.putString(ProxyActivity.PROXY_FRAGMENT_CLASS_KEY, _Class.name) // com.project.app.activity.*
            mIntent.putExtras(bundle)
        }
        startActivityForResult(mIntent, requestCode, options)
    }


}