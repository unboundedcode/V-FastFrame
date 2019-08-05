package com.vension.fastframe.module_news.mvp.presenter

import com.vension.fastframe.module_news.RxHandlerNews
import com.vension.fastframe.module_news.api.NewsApiService
import com.vension.fastframe.module_news.bean.NewsDetailModel
import com.vension.fastframe.module_news.mvp.contract.NewsDetailContract
import kv.vension.vframe.core.mvp.AbsPresenter
import kv.vension.vframe.net.RetrofitHelper
import kv.vension.vframe.net.exception.ExceptionHandle
import kv.vension.vframe.net.rx.RxHandler
import lib.vension.fastframe.common.HttpConfig

/**
 * ===================================================================
 * @author: Created by Vension on 2019/8/2 17:05.
 * @email:  250685***4@qq.com
 * @update: update by *** on 2019/8/2 17:05
 * @desc:
 * ===================================================================
 */
class NewsDetailPresenter : AbsPresenter<NewsDetailContract.View>(),NewsDetailContract.Presenter {

    override fun deatailItemRequest(articleUrl: String) {
        mView?.showProgressDialog()
        addSubscription(
            RetrofitHelper.getService(HttpConfig.BASE_URL_NEWS,NewsApiService::class.java)
                .getNewsDetail(articleUrl)
                .compose(RxHandler.rxSchedulerObservableToMain())
                .compose(RxHandlerNews.handleObservableNewsResult<NewsDetailModel>())
                .subscribe(
                    {
                        run {
                            mView?.dismissProgressDialog()
                            mView?.detailItemData(it)
                        }
                    },
                    { t->
                        mView?.dismissProgressDialog()
                        mView?.let {
                            ExceptionHandle.handleException(t, it)
                        }
                    }
                )
        )
    }

}