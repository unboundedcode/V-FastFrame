package com.vension.fastframe.module_wan.mvp.presenter

import com.vension.fastframe.module_wan.api.ApiService
import com.vension.fastframe.module_wan.bean.Article
import com.vension.fastframe.module_wan.bean.ArticleResponseBody
import com.vension.fastframe.module_wan.bean.HttpResult
import com.vension.fastframe.module_wan.mvp.contract.KnowledgeContract
import kv.vension.vframe.net.RetrofitHelper
import kv.vension.vframe.net.exception.ExceptionHandle
import kv.vension.vframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig

/**
 * ========================================================
 * @author: Created by Vension on 2018/11/21 11:06.
 * @email:  2506856664@qq.com
 * @desc:   character determines attitude, attitude determines destiny
 * ========================================================
 */
class KnowledgePresenter : CommonPresenter<Article, KnowledgeContract.View<Article>>(),
    KnowledgeContract.Presenter<Article>{


    override fun requestKnowledgeList(page: Int, cid: Int) {
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_WANANDROID, ApiService::class.java)
                .getKnowledgeList(page, cid)
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandler.handleObservableResult<HttpResult<ArticleResponseBody>>())
                .subscribe(
                    {
                        run {
                            mView?.setKnowledgeList(page,it.data)
                        }
                    },
                    {t->
                        mView?.dismissProgressDialog()
                        mView?.let {
                            ExceptionHandle.handleException(t, it)
                        }
                    }
                )
        )
    }

}