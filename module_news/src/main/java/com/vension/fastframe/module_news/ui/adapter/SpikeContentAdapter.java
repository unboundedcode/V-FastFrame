package com.vension.fastframe.module_news.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vension.fastframe.module_news.R;
import com.vension.fastframe.module_news.bean.HomeTophotIndex;
import com.vension.fastframe.module_news.utils.ToastUitl;
import lib.vension.fastframe.common.utils.GlideImageLoader;

import java.util.List;

/**
 * @author：dingcl
 * 横向新闻列表
 * 热闻
 */

public class SpikeContentAdapter extends BaseQuickAdapter<HomeTophotIndex.Articles.HotSpecialNews, BaseViewHolder> {
    private Context context;
    public SpikeContentAdapter(Context context,int layoutResId, List<HomeTophotIndex.Articles.HotSpecialNews> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeTophotIndex.Articles.HotSpecialNews hotSpecialNews) {
        int adapterPosition = helper.getAdapterPosition();
        if (hotSpecialNews != null) {

            new GlideImageLoader().displayImage(mContext,hotSpecialNews.pics.get(0).url,helper.getView(R.id.article_img));
            helper.setText(R.id.horiTitle, hotSpecialNews.title == null ? "" : hotSpecialNews.title);

            FrameLayout view = helper.getView(R.id.flHoriRoot);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUitl.showShort("横向："+adapterPosition+hotSpecialNews.title);
//                    HotNewsDetailActivity.newInstance(context, hotSpecialNews.articleUrl == null ? "" : hotSpecialNews.articleUrl);
                }
            });
        }
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.item_index_horizontal_list_item,null);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (0.286 * DensityUtil.getScreenWidth(mContext)), LinearLayout.LayoutParams.WRAP_CONTENT);
//        view.setLayoutParams(params);
        return view;

    }

}
