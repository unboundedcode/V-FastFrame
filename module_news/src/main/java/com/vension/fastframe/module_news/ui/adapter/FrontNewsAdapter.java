package com.vension.fastframe.module_news.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vension.fastframe.module_news.NewsConstant;
import com.vension.fastframe.module_news.R;
import com.vension.fastframe.module_news.bean.FrontNewsModel;
import com.vension.fastframe.module_news.utils.StringUtils;
import kv.vension.vframe.glide.GlideApp;
import kv.vension.vframe.views.ShapeImageView;

import java.util.List;
/**
 */
public class FrontNewsAdapter extends BaseQuickAdapter<FrontNewsModel.Articles, BaseViewHolder> {
    private Context mcontext;

    public FrontNewsAdapter(int layoutResId, List<FrontNewsModel.Articles> data) {
        super(layoutResId, data);
    }

    public FrontNewsAdapter(Context context,int layoutResId, List<FrontNewsModel.Articles> data) {
        super(layoutResId, data);
        mcontext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FrontNewsModel.Articles articles) {
        int adapterPosition = helper.getAdapterPosition();
        ShapeImageView imgTop = helper.getView(R.id.imgTop);
        TextView tvBottom = helper.getView(R.id.tvBottom);
        TextView tvStatus = helper.getView(R.id.tvStatus);
        LinearLayout llJoin =  helper.getView(R.id.llJoin);
        TextView tvGUBINum =  helper.getView(R.id.tvGUBINum);
        TextView tvFlagJoinNum =  helper.getView(R.id.tvFlagJoinNum);
        helper.setText(R.id.tvBottom, articles.title == null ? "" : articles.title);
        GlideApp.with(mContext).load(articles.pics.get(0) == null ? "" : articles.pics.get(0).url).into(imgTop);
        if (NewsConstant.FRONT_NEWS_RIGHT_BOTTOM_RIGHTNOW == articles.isjoinOrPublish){//马上参与
            imgTop.setAlpha(255);
            tvStatus.setVisibility(View.GONE);
            llJoin.setVisibility(View.VISIBLE);
            tvGUBINum.setVisibility(View.GONE);
            tvFlagJoinNum.setText(StringUtils.getDecimalWithW(articles.beanpool,1,false)+"万咕币");

        }else if (NewsConstant.FRONT_NEWS_WAIT_PUBLISH == articles.isjoinOrPublish){//待揭晓
            imgTop.setAlpha(150);
            tvStatus.setVisibility(View.VISIBLE);
            llJoin.setVisibility(View.GONE);
            tvStatus.setText("待揭晓");
            tvGUBINum.setVisibility(View.VISIBLE);
            tvGUBINum.setText(StringUtils.getDecimalWithW(articles.beanpool,1,true)+"万咕币");

        }else if (NewsConstant.FRONT_NEWS_ALRIGHT_PUBLISH == articles.isjoinOrPublish){//已揭晓
            imgTop.setAlpha(150);
            tvStatus.setVisibility(View.VISIBLE);
            llJoin.setVisibility(View.GONE);
            tvStatus.setText("已揭晓");
            tvGUBINum.setVisibility(View.VISIBLE);
            tvGUBINum.setText(StringUtils.getDecimalWithW(articles.beanpool,1,true)+"万咕币");
        }
    }

}
