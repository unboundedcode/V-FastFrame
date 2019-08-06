package kv.vension.fastframe.core.adapter.recy.decoration;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/3/30 16:46
 * 描  述：val mItemDecoration = RecGridDividerItemDecoration(GridLayoutManager(getActivity(), 3) as GridLayoutManager, 5, true)
 *        refreshRecyclerView.addItemDecoration(mItemDecoration)
 * ========================================================
 */

public class RecGridDividerItemDecoration extends RecyclerView.ItemDecoration {

	private int spanCount;
	private int spacing;
	private boolean includeEdge;

	public RecGridDividerItemDecoration(GridLayoutManager layoutManager, int spacing, boolean includeEdge) {
		this(layoutManager.getSpanCount(), spacing, includeEdge);
	}

	public RecGridDividerItemDecoration(int spanCount, int spacing, boolean includeEdge) {
		this.spanCount = spanCount;
		this.spacing = spacing;
		this.includeEdge = includeEdge;
	}


	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		int position = parent.getChildAdapterPosition(view); // item position
		int column = position % spanCount; // item column

		if (includeEdge) {
			outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
			outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

			if (position < spanCount) { // top edge
				outRect.top = spacing;
			}
			outRect.bottom = spacing; // item bottom
		} else {
			outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
			outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
			if (position >= spanCount) {
				outRect.top = spacing; // item top
			}
		}
	}



}
