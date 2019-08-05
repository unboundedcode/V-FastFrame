package com.vension.fastframe.module_news.ui.adapter;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.vension.fastframe.module_news.NewsConstant;
import com.vension.fastframe.module_news.R;
import com.vension.fastframe.module_news.bean.NewsChannelTable;
import com.vension.fastframe.module_news.event.ChannelItemMoveEvent;
import com.vension.fastframe.module_news.listener.OnNoDoubleClickListener;
import com.vension.fastframe.module_news.widget.ItemDragHelperCallback;
import com.vension.fastframe.module_news.widget.ViewHolderHelper;
import io.reactivex.subjects.Subject;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 新闻频道
 */
public class ChannelAdapter  extends CommonRecycleViewAdapter<NewsChannelTable>implements
        ItemDragHelperCallback.OnItemMoveListener{

    private ItemDragHelperCallback mItemDragHelperCallback;
    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setItemDragHelperCallback(ItemDragHelperCallback itemDragHelperCallback) {
        mItemDragHelperCallback = itemDragHelperCallback;
    }
    public ChannelAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(ViewHolderHelper helper, NewsChannelTable newsChannelTable) {
        helper.setText(R.id.news_channel_tv,newsChannelTable.getNewsChannelName());
        if (newsChannelTable.getNewsChannelFixed()) {
//            helper.setTextColor(R.id.news_channel_tv,ContextCompat.getColor(mContext,R.color.gray));
            helper.setTextColor(R.id.news_channel_tv, ContextCompat.getColor(mContext,R.color.color_orange));
        }else{
            helper.setTextColor(R.id.news_channel_tv,ContextCompat.getColor(mContext,R.color.color_gray));
        }
        handleLongPress(helper,newsChannelTable);
        handleOnClick(helper,newsChannelTable);
    }


    private void handleLongPress(ViewHolderHelper helper,final NewsChannelTable newsChannelTable) {
        if (mItemDragHelperCallback != null) {
            helper.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mItemDragHelperCallback.setLongPressEnabled(newsChannelTable.getNewsChannelIndex()==0?false:true);
                    return false;
                }
            });
        }
    }

    private void handleOnClick(final ViewHolderHelper helper,final NewsChannelTable newsChannelTable) {
        if (mOnItemClickListener != null) {
            helper.itemView.setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    if (!newsChannelTable.getNewsChannelFixed()) {
                        mOnItemClickListener.onItemClick(v, helper.getLayoutPosition());
                    }
                }
            });
        }
    }

    @SuppressWarnings("rawtypes")
    private ConcurrentHashMap<Object, List<Subject>> subjectMapper = new ConcurrentHashMap<Object, List<Subject>>();
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (isChannelFixed(fromPosition, toPosition)) {
            return false;
        }
        Collections.swap(getAll(), fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        List<Subject> subjectList = subjectMapper.get(NewsConstant.CHANNEL_SWAP);
        if (!isEmpty(subjectList)) {
            for (Subject subject : subjectList) {
                subject.onNext(new ChannelItemMoveEvent(fromPosition, toPosition));
            }
        }
//        RxBus.getInstance().post(AppConstant.CHANNEL_SWAP,new ChannelItemMoveEvent(fromPosition, toPosition));
        return true;
    }

    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Collection<Subject> collection) {
        return null == collection || collection.isEmpty();
    }

    private boolean isChannelFixed(int fromPosition, int toPosition) {
        return (getAll().get(fromPosition).getNewsChannelFixed() ||
                getAll().get(toPosition).getNewsChannelFixed())&&(fromPosition==0||toPosition==0);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
