package com.vension.fastframe.module_wan.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.fastframe.module_wan.Constant
import com.vension.fastframe.module_wan.R
import com.vension.fastframe.module_wan.bean.Article
import com.vension.fastframe.module_wan.bean.ArticleResponseBody
import com.vension.fastframe.module_wan.bean.Banner
import com.vension.fastframe.module_wan.mvp.contract.HomeContract
import com.vension.fastframe.module_wan.mvp.presenter.HomePresenter
import com.vension.fastframe.module_wan.ui.adapter.HomeAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.item_recy_wan_home_banner.view.*
import kv.vension.vframe.core.mvp.AbsCompatMVPRefreshFragment
import kv.vension.vframe.ext.showToast
import kv.vension.vframe.glide.ImageLoader
import kv.vension.vframe.utils.NetWorkUtil
import kv.vension.vframe.utils.SpUtils

/**
 * ===================================================================
 * @author: Created by Vension on 2019/7/23 14:18.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/7/23 14:18
 * @desc:   character determines attitude, attitude determines destiny
 * ===================================================================
 */
class MenuTabHomeFragment : AbsCompatMVPRefreshFragment<Article, HomeContract.View<Article>,HomePresenter>(),HomeContract.View<Article> {

    companion object {
        fun getInstance(): MenuTabHomeFragment = MenuTabHomeFragment()
    }
    private var isLogin: Boolean = SpUtils.getBoolean(Constant.LOGIN_KEY, false)
    private lateinit var bannerDatas: ArrayList<Banner>
    private var bannerView: View? = null
    private var isRefresh = true

    /**
     * Banner Adapter
     */
    private val bannerAdapter: BGABanner.Adapter<ImageView, String> by lazy {
        BGABanner.Adapter<ImageView, String> { bgaBanner, imageView, feedImageUrl, position ->
            ImageLoader.loadImage(activity!!,imageView,feedImageUrl!!)
        }
    }

    /**
     * BannerClickListener
     */
    private val bannerDelegate = BGABanner.Delegate<ImageView, String> { banner, imageView, model, position ->
        if (bannerDatas.size > 0) {
            val data = bannerDatas[position]
            val mBundle = getBundleExtras()
            mBundle.putString("web_url",data.url)
            mBundle.putString("web_title",data.title)
            mBundle.putInt("web_id",data.id)
            startProxyActivity(AgentWebFragment::class.java,mBundle)
        }
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun getRefreshColor(color: Int): Int {
        return super.getRefreshColor(R.color.colorWanMain)
    }

    override fun createPresenter(): HomePresenter {
        return HomePresenter()
    }

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        super.initViewAndData(view, savedInstanceState)
        bannerView = layoutInflater.inflate(R.layout.item_recy_wan_home_banner, null)
        bannerView?.banner?.run {
            setDelegate(bannerDelegate)
        }
    }

    override fun createRecyAdapter(): BaseQuickAdapter<Article, BaseViewHolder> {
        return HomeAdapter(context)
    }

    override fun addItemClickListener(mAdapter: BaseQuickAdapter<Article, BaseViewHolder>) {
        mAdapter?.setOnItemClickListener { adapter, view, position ->
            val item  = mAdapter?.getItem(position) as Article
            item?.let {
                val mBundle = getBundleExtras()
                mBundle.putString("web_url",it.link)
                mBundle.putString("web_title",it.title)
                mBundle.putInt("web_id",it.id)
                startProxyActivity(AgentWebFragment::class.java,mBundle)
            }
        }
    }

    override fun addItemChildClickListener(mAdapter: BaseQuickAdapter<Article, BaseViewHolder>) {
        super.addItemChildClickListener(mAdapter)
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            val item  = adapter?.getItem(position) as Article
            item?.let {
                when(view.id){
                    R.id.iv_like -> {
                        if (isLogin) {
                            if (!NetWorkUtil.isNetworkAvailable()) {
                                showToast(resources.getString(R.string.no_network_view_hint))
                                return@setOnItemChildClickListener
                            }
                            val collect = item.collect
                            item.collect = !collect
                            mAdapter.setData(position, item)
                            if (collect) {
                                mPresenter?.cancelCollectArticle(item.id)
                            } else {
                                mPresenter?.addCollectArticle(item.id)
                            }
                        } else {
                            startProxyActivity(LoginFragment::class.java)
                            showToast(resources.getString(R.string.login_tint))
                        }
                    }
                    else -> {

                    }
                }
            }
        }
    }

    override fun onTargetRequestApi(isRefresh: Boolean, page: Int, limit: Int) {
        this.isRefresh = isRefresh
        mPresenter?.requestHomeData()
    }

    override fun onLoadMoreRequested() {
        isRefresh = false
        stopRefresh(false)
        val page = mRecyAdapter.data.size / 20
        mPresenter?.requestArticles(page)
    }

    override fun setBanners(banners: List<Banner>) {
        bannerDatas = banners as ArrayList<Banner>
        val bannerFeedList = ArrayList<String>()
        val bannerTitleList = ArrayList<String>()
        Observable.fromIterable(banners)
            .subscribe { list ->
                bannerFeedList.add(list.imagePath)
                bannerTitleList.add(list.title)
            }
        bannerView?.banner?.run {
            setAutoPlayAble(bannerFeedList.size > 1)
            setData(bannerFeedList, bannerTitleList)
            setAdapter(bannerAdapter)
        }
    }

    override fun setArticles(articles: ArticleResponseBody) {
        val headers = ArrayList<View>()
        headers.add(bannerView!!)
        if (this.isRefresh) setRefreshData(articles.datas, headers,null) else setMoreData(articles.datas)
    }

    override fun showCollectSuccess(success: Boolean) {
        if (success) {
            showToast(resources.getString(R.string.collect_success))
        }
    }

    override fun showCancelCollectSuccess(success: Boolean) {
        if (success) {
            showToast(resources.getString(R.string.cancel_collect_success))
        }
    }

}