package com.vension.fastframe.module_wan.ui.fragment

import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.leifu.mvpkotlin.util.PreferenceUtil
import com.vension.fastframe.module_wan.Constant
import com.vension.fastframe.module_wan.R
import com.vension.fastframe.module_wan.bean.Article
import com.vension.fastframe.module_wan.bean.ArticleResponseBody
import com.vension.fastframe.module_wan.mvp.contract.KnowledgeContract
import com.vension.fastframe.module_wan.mvp.presenter.KnowledgePresenter
import com.vension.fastframe.module_wan.ui.adapter.KnowledgeAdapter
import kv.vension.vframe.core.mvp.AbsCompatMVPRefreshFragment
import kv.vension.vframe.ext.showToast
import kv.vension.vframe.utils.NetWorkUtil

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/20 16:59.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
class KnowledgeFragment : AbsCompatMVPRefreshFragment<Article, KnowledgeContract.View<Article>, KnowledgePresenter>(),
    KnowledgeContract.View<Article> {

    private var isLogin: Boolean by PreferenceUtil(Constant.LOGIN_KEY, false)

    companion object {
        fun getInstance(cid: Int): KnowledgeFragment {
            val fragment = KnowledgeFragment()
            val args = Bundle()
            args.putInt(Constant.CONTENT_CID_KEY, cid)
            fragment.arguments = args
            return fragment
        }
    }

    /**
     * cid
     */
    private var cid: Int = 0


    override fun showToolBar(): Boolean {
        return false
    }

    override fun createRecyAdapter(): KnowledgeAdapter {
        return KnowledgeAdapter(activity)
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

    override fun initViewAndData(view: View, savedInstanceState: Bundle?) {
        super.initViewAndData(view, savedInstanceState)
        cid = arguments?.getInt(Constant.CONTENT_CID_KEY) ?: 0
    }

    override fun onTargetRequestApi(isRefresh: Boolean, page: Int, limit: Int) {
        mPresenter?.requestKnowledgeList(page, cid)
    }

    override fun createPresenter(): KnowledgePresenter {
        return  KnowledgePresenter()
    }

    override fun setKnowledgeList(page: Int,articles: ArticleResponseBody) {
        articles.datas.let {
            if(page > 0) setMoreData(it) else setRefreshData(it)
        }
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