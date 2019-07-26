package kv.vension.vframe.core

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
import kv.vension.vframe.R
import kv.vension.vframe.cache.PageCache
import kv.vension.vframe.dialog.LoadingDialog
import kv.vension.vframe.event.NetworkChangeEvent
import kv.vension.vframe.views.MultiStateLayout
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/15 12:12
 * 更 新：2019/7/15 12:12
 * 描 述：Fragment-基类
 * ========================================================
 */

abstract class AbsCompatFragment : Fragment(), IFragment,View.OnClickListener {

    val TAG = this.javaClass.simpleName //获取上下文并设置log标记

    /**
     * 在使用自定义toolbar时候的根布局 = toolBarView+childView
     */
    private var rootView: View? = null
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
     * butterknife Unbinder
     */
//    private lateinit var mUnBinder: Unbinder
    /**
     * 视图是否准备完毕
     */
    private var isPagePrepare = false
    /**
     * 数据是否加载过了
     */
    private var hasLoadData = false

    /**
     * LoadingDialog
     */
    protected val mLoadingDialog: LoadingDialog by lazy { LoadingDialog.Builder(context!!).setCancelable(false).setCancelOutside(false).create() }

    lateinit var mContext: Context
    lateinit var mActivity: Activity

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
                rootView = inflater.inflate(if (isToolbarCover())
                    R.layout.activity_base_toolbar_cover
                else
                    R.layout.activity_base, container, false)//根布局
                //toolbar容器
                val vsToolbar = rootView?.findViewById<View>(R.id.vs_toolbar) as ViewStub
                //子布局容器
                val flContainer = rootView?.findViewById<View>(R.id.fl_container) as FrameLayout
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
            mCommonTitleBar = rootView?.findViewById<View>(R.id.commonTitleBar) as CommonTitleBar//子布局容器
            initToolBar(mCommonTitleBar)//初始化标题栏
        }
        //绑定view
//        mUnBinder = ButterKnife.bind(this, view)
        mRxPermissions = RxPermissions(this)
        //开启事件总线
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }

        //初始化view和数据
        initViewAndData(view,savedInstanceState)
        //页面初始化完成后进行懒加载数据
        lazyLoadDataIfPrepared()
        //多种状态切换的view 重试点击事件
        fraMultiStateLayout?.let {
            it.setOnRetryClickListener(mRetryClickListener)
        }
        PageCache.pageFragmentCache.add(this)
    }

    override fun onHiddenChanged(hidden: Boolean) {//单个fragment
        super.onHiddenChanged(hidden)
    }

    //结合viewpager
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //如果fragment可见进行懒加载数据,setUserVisibleHint()有可能在fragment的生命周期外被调用
        //如果view还未初始化，不进行处理
        if(isPagePrepare){
            rootView?.let {
                if (isVisibleToUser) {
                    //页面对用户可见且初始化完成时进行懒加载数据
                    lazyLoadDataIfPrepared()
                }
            }
        }
    }


    /**
     * 页面初始化完进行懒加载
     * 第三步:lazyLoadDataIfPrepared()方法中进行双重标记判断,通过后即可进行数据加载
     */
    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isPagePrepare && !hasLoadData) {
            lazyLoadData()
            hasLoadData = true
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
//        mUnBinder.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        rootView = null
        if(mLoadingDialog.isShowing){
            mLoadingDialog.dismiss()
        }
        resetVariavle()
        PageCache.pageFragmentCache.remove(this)//
    }

    override fun onClick(v: View?) {

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
     * 请求加载网络数据
     * 第四步:定义抽象方法lazyLoadData(),具体加载数据的工作,交给子类去完成
     */
    abstract fun lazyLoadData()
    /**
     * 无网状态—>有网状态 的自动重连操作，子类可重写该方法
     */
    open fun doReConnected() {
        lazyLoadData()
    }


    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        lazyLoadData()
    }


    /**重置变量 */
    private fun resetVariavle() {
        isPagePrepare = false
        hasLoadData = false
    }

    /**
     * 获取自定义toolbarview 资源id 默认为-1，
     * showToolBar()方法必须返回true才有效
     */
    private fun getToolBarResId(): Int {
        return getToolBarResId(0)
    }

    open fun getToolBarResId(layout : Int): Int {
        return if (layout > 0) layout else R.layout.layout_default_toolbar
    }


    override fun <V : View> findViewById(id: Int): V {
        return rootView?.findViewById(id) as V
    }

    /**初始化标题栏*/
    open fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        mCommonTitleBar.setListener {_: View?, action: Int, extra: String? ->
            if (action == CommonTitleBar.ACTION_LEFT_BUTTON || action == CommonTitleBar.ACTION_LEFT_TEXT) {
                activity?.onBackPressed()
            }
        }
    }


    /**
     * 网络状态变化事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangeEvent(event: NetworkChangeEvent) {
        if (event.isConnected) {
            doReConnected()
        }
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

//    override fun postBackStack(fragment: Fragment) {
//        (activity as AbsCompatActivity).postBackStack(fragment)
//    }
//
//    override fun popBackStack() {
//        (activity as AbsCompatActivity).popBackStack()
//    }
//
//    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//        if (KeyEvent.KEYCODE_BACK == keyCode) {
//            popBackStack()
//            return true
//        }
//        return false
//    }
//
//    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
//        return false
//    }


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