package com.vension.fastframe.app.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.android.material.navigation.NavigationView
import com.vension.fastframe.app.R
import com.wuhenzhizao.titlebar.statusbar.StatusBarUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar_main.*
import kv.vension.vframe.core.AbsCompatActivity
import kv.vension.vframe.ext.showToast
import lib.vension.fastframe.common.RouterConfig
import lib.vension.fastframe.common.test.TestFragment
import lib.vension.fastframe.common.test.TestRefreshFragment



/**
 * ========================================================
 * 作 者：Vension
 * 日 期：2019/7/15 17:40
 * 更 新：2019/7/15 17:40
 * 描 述：首页
 * ========================================================
 */

@Route(path = RouterConfig.PATH_APP_MAINACTIVITY)
class MainActivity : AbsCompatActivity(){

    private var mTabFragment_1: TestFragment? = null
    private var mTabFragment_2: TestFragment? = null
    private var mTabFragment_3: TestFragment? = null
    private var mTabFragment_4: TestFragment? = null

    //默认为0
    private var mIndex = 0

    private val mNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.menu_tab_1 -> {
                switchFragment(0)
            }
            R.id.menu_tab_2 -> {
                switchFragment(1)
            }
            R.id.menu_tab_3 -> {
                switchFragment(2)
            }
            R.id.menu_tab_4 -> {
                switchFragment(3)
            }
        }
        true
    }

    /**
     * NavigationView 监听
     */
    private val onDrawerNavigationItemSelectedListener =
        NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_wanAndroid -> {//wanAndroid
                    ARouter.getInstance().build(RouterConfig.PATH_WAN_MAINACTIVITY).navigation()
                }
                R.id.nav_weike ->{//微课
                    ARouter.getInstance().build(RouterConfig.PATH_COURSE_MAINACTIVITY).navigation()
                }
                R.id.nav_news -> {//新闻
                    ARouter.getInstance().build(RouterConfig.PATH_NEWS_MAINACTIVITY).navigation()
                }
                R.id.nav_ShoppingMall ->{//商城
                    showToast("敬请期待")
                }
                R.id.nav_test -> {
                    val bundle = Bundle()
                    bundle.putString("key_content","测试")
                    startProxyActivity(NetTestFragment::class.java)
                }
                R.id.nav_test_mvpRefresh -> {
                    startProxyActivity(TestRefreshFragment::class.java)
                }
            }
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt("currTabIndex",0)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //记录fragment的位置,防止崩溃 activity被系统回收时，fragment错乱
        outState.putInt("currTabIndex", mIndex)
    }

    override fun isSupportSwipeBack(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int = R.layout.activity_main

    override fun initViewAndData(savedInstanceState: Bundle?) {
        toolbar_home.run {
            title = getString(R.string.app_name)
            setSupportActionBar(this)
        }

        drawer_layout.run {
            var toggle = ActionBarDrawerToggle(this@MainActivity, this, toolbar_home,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            addDrawerListener(toggle)
            toggle.syncState()
        }

        mBottomNavigationView.run {
            // 以前使用 BottomNavigationViewHelper.disableShiftMode(this) 方法来设置底部图标和字体都显示并去掉点击动画
            // 升级到 28.0.0 之后，官方重构了 BottomNavigationView ，目前可以使用 labelVisibilityMode = 1 来替代
            // BottomNavigationViewHelper.disableShiftMode(this)
            labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
            setOnNavigationItemSelectedListener(mNavigationItemSelectedListener)
            menu.getItem(mIndex).isChecked = true
        }

        navigationView_left.run {
            setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener)
        }

        switchFragment(mIndex)
    }

    /**
     * 切换Fragment
     * @param position 下标
     */
    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        mIndex = position
        when (position) {
            0 ->{
                toolbar_home.title = getString(R.string.menu_tab_1)
                mTabFragment_1?.let {
                    transaction.show(it)
                } ?: TestFragment.getInstance("首页").let {
                    mTabFragment_1 = it
                    transaction.add(R.id.container_main, it, "home")
                }
            }
            1 -> {
                toolbar_home.title = getString(R.string.menu_tab_2)
                mTabFragment_2?.let {
                    transaction.show(it)
                } ?: TestFragment.getInstance("热门").let {
                    mTabFragment_2 = it
                    transaction.add(R.id.container_main, it, "hot")
                }
            }
            2 -> {
                toolbar_home.title = getString(R.string.menu_tab_3)
                mTabFragment_3?.let {
                    transaction.show(it)
                } ?: TestFragment.getInstance("发现").let {
                    mTabFragment_3 = it
                    transaction.add(R.id.container_main, it, "discover")
                }
            }
            3 -> {
                toolbar_home.title = getString(R.string.menu_tab_4)
                mTabFragment_4?.let {
                    transaction.show(it)
                } ?: TestFragment.getInstance("我的").let {
                    mTabFragment_4 = it
                    transaction.add(R.id.container_main, it, "mine")
                }
            }
            else -> {
                transaction.add(R.id.container_main, Fragment(), "test")
            }
        }
        //
        /*
        *  如果你需要同步的操作, 并且你不需要加到back stack里, 使用commitNow().
        *  如果你操作很多transactions, 并且不需要同步, 或者你需要把transactions加在back stack里, 那就使用commit().
        *  如果你希望在某一个指定的点, 确保所有的transactions都被执行, 那么使用executePendingTransactions().
        *
        */
        transaction.commit()
//        transaction.commitAllowingStateLoss()
    }


    /**
     * 隐藏所有的Fragment
     * @param transaction transaction
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        mTabFragment_1?.let { transaction.hide(it) }
        mTabFragment_2?.let { transaction.hide(it) }
        mTabFragment_3?.let { transaction.hide(it) }
        mTabFragment_4?.let { transaction.hide(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_bottom, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.home ->{
                drawer_layout.openDrawer(GravityCompat.START)
            }
            else ->{
                Toast.makeText(this@MainActivity,item?.title.toString(),Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mTabFragment_1 = null
        mTabFragment_2 = null
        mTabFragment_3 = null
        mTabFragment_4 = null
    }

    private var exitTime: Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis().minus(exitTime) <= 2000) {
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            }
            finish()
        } else {
            exitTime = System.currentTimeMillis()
            Toast.makeText(this@MainActivity,"再按一次退出程序",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        //TODO 暂时解决播放视频返回时底部Tab上移bug,待优化
        StatusBarUtils.transparentStatusBar(window)//代码设置透明状态栏
    }

    override fun recreate() {
        try {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            if (mTabFragment_1 != null) {
                fragmentTransaction.remove(mTabFragment_1!!)
            }
            if (mTabFragment_2 != null) {
                fragmentTransaction.remove(mTabFragment_2!!)
            }
            if (mTabFragment_3 != null) {
                fragmentTransaction.remove(mTabFragment_3!!)
            }
            if (mTabFragment_4 != null) {
                fragmentTransaction.remove(mTabFragment_4!!)
            }
            fragmentTransaction.commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.recreate()
    }


}
