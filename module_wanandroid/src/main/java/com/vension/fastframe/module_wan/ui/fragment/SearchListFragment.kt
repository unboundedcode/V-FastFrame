package com.vension.fastframe.module_wan.ui.fragment

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kv.vension.fastframe.utils.PreferenceUtil
import com.vension.fastframe.module_wan.Constant
import com.vension.fastframe.module_wan.R
import com.vension.fastframe.module_wan.bean.Article
import com.vension.fastframe.module_wan.bean.ArticleResponseBody
import com.vension.fastframe.module_wan.mvp.contract.SearchListContract
import com.vension.fastframe.module_wan.mvp.presenter.SearchListPresenter
import com.vension.mvpforkotlin.sample.ui.adapter.SearchListAdapter
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kv.vension.fastframe.VFrame
import kv.vension.fastframe.core.mvp.AbsCompatMVPRefreshFragment
import kv.vension.fastframe.ext.showToast
import kv.vension.fastframe.utils.NetworkUtil

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/11/23 16:26
 * 描  述：搜索结果列表
 * ========================================================
 */

class SearchListFragment : AbsCompatMVPRefreshFragment<Article, SearchListContract.View<Article>, SearchListPresenter>(),
    SearchListContract.View<Article> {

    private lateinit var keyStr:String
    private var isLogin: Boolean by PreferenceUtil(Constant.LOGIN_KEY, false)

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        super.initToolBar(mCommonTitleBar)
        mCommonTitleBar.setBackgroundColor(VFrame.getColor(R.color.colorWanMain))
        keyStr = getBundleExtras().getString(Constant.SEARCH_KEY)!!
        mCommonTitleBar.centerTextView.text = keyStr
    }

    override fun createPresenter(): SearchListPresenter {
        return SearchListPresenter()
    }

    override fun createRecyAdapter(): SearchListAdapter {
        return SearchListAdapter(context)
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
            val item  = mAdapter?.getItem(position) as Article
            item?.let {
                when(view.id){
                    R.id.iv_like -> {
                        if (isLogin) {
                            if (!NetworkUtil.isNetworkAvailable()) {
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
        mPresenter?.queryBySearchKey(page, keyStr)
    }

    override fun showArticles(page: Int,articles: ArticleResponseBody) {
        if(page > 0) setMoreData(articles.datas) else setRefreshData(articles.datas)
    }

    override fun showCollectSuccess(success: Boolean) {
        if (success) {
            showToast(getString(R.string.collect_success))
        }
    }

    override fun showCancelCollectSuccess(success: Boolean) {
        if (success) {
            showToast(getString(R.string.cancel_collect_success))
        }
    }

}