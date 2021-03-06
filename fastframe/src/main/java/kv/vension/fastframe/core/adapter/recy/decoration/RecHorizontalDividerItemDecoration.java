package kv.vension.fastframe.core.adapter.recy.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.DimenRes;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/3/30 16:46
 * 描  述：val mItemDecoration = RecHorizontalDividerItemDecoration.Builder(activity)
 .color(ContextCompat.getColor(activity!!, R.color.Color_dddddd))
 .size(VSizeUtil.dip2px(1.0f))
 .margin(VSizeUtil.dip2px(18.0f), 0)
 .build()
 refreshRecyclerView.addItemDecoration(mItemDecoration)

 recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
 .paintProvider(adapter)
 .visibilityProvider(adapter)
 .marginProvider(adapter)
 .build());
 * ========================================================
 */

public class RecHorizontalDividerItemDecoration extends RecFlexibleDividerDecoration {

	private MarginProvider mMarginProvider;

	protected RecHorizontalDividerItemDecoration(Builder builder) {
		super(builder);
		mMarginProvider = builder.mMarginProvider;
	}

	@Override
	protected Rect getDividerBound(int position, RecyclerView parent, View child) {
		Rect bounds = new Rect(0, 0, 0, 0);
		int transitionX = (int) ViewCompat.getTranslationX(child);
		int transitionY = (int) ViewCompat.getTranslationY(child);
		RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
		bounds.left = parent.getPaddingLeft() +
				mMarginProvider.dividerLeftMargin(position, parent) + transitionX;
		bounds.right = parent.getWidth() - parent.getPaddingRight() -
				mMarginProvider.dividerRightMargin(position, parent) + transitionX;

		int dividerSize = getDividerSize(position, parent);
		if (mDividerType == DividerType.DRAWABLE) {
			// set top and bottom position of divider
			if (mPositionInsideItem) {
				bounds.bottom = child.getBottom() + params.topMargin + transitionY;
				bounds.top = bounds.bottom - dividerSize;
			} else {
				bounds.top = child.getBottom() + params.topMargin + transitionY;
				bounds.bottom = bounds.top + dividerSize;
			}
		} else {
			// set center point of divider
			if (mPositionInsideItem) {
				bounds.top = child.getBottom() + params.topMargin - dividerSize / 2 + transitionY;
			} else {
				bounds.top = child.getBottom() + params.topMargin + dividerSize / 2 + transitionY;
			}
			bounds.bottom = bounds.top;
		}

		return bounds;
	}

	@Override
	protected void setItemOffsets(Rect outRect, int position, RecyclerView parent) {
		if (mPositionInsideItem) {
			outRect.set(0, 0, 0, 0);
		} else {
			outRect.set(0, 0, 0, getDividerSize(position, parent));
		}
	}

	private int getDividerSize(int position, RecyclerView parent) {
		if (mPaintProvider != null) {
			return (int) mPaintProvider.dividerPaint(position, parent).getStrokeWidth();
		} else if (mSizeProvider != null) {
			return mSizeProvider.dividerSize(position, parent);
		} else if (mDrawableProvider != null) {
			Drawable drawable = mDrawableProvider.drawableProvider(position, parent);
			return drawable.getIntrinsicHeight();
		}
		throw new RuntimeException("failed to get size");
	}

	/**
	 * Interface for controlling divider margin
	 */
	public interface MarginProvider {

		/**
		 * Returns left margin of divider.
		 *
		 * @param position Divider position (or group index for GridLayoutManager)
		 * @param parent   RecyclerView
		 * @return left margin
		 */
		int dividerLeftMargin(int position, RecyclerView parent);

		/**
		 * Returns right margin of divider.
		 *
		 * @param position Divider position (or group index for GridLayoutManager)
		 * @param parent   RecyclerView
		 * @return right margin
		 */
		int dividerRightMargin(int position, RecyclerView parent);
	}

	public static class Builder extends RecFlexibleDividerDecoration.Builder<Builder> {

		private MarginProvider mMarginProvider = new MarginProvider() {
			@Override
			public int dividerLeftMargin(int position, RecyclerView parent) {
				return 0;
			}

			@Override
			public int dividerRightMargin(int position, RecyclerView parent) {
				return 0;
			}
		};

		public Builder(Context context) {
			super(context);
		}

		public Builder margin(final int leftMargin, final int rightMargin) {
			return marginProvider(new MarginProvider() {
				@Override
				public int dividerLeftMargin(int position, RecyclerView parent) {
					return leftMargin;
				}

				@Override
				public int dividerRightMargin(int position, RecyclerView parent) {
					return rightMargin;
				}
			});
		}

		public Builder margin(int horizontalMargin) {
			return margin(horizontalMargin, horizontalMargin);
		}

		public Builder marginResId(@DimenRes int leftMarginId, @DimenRes int rightMarginId) {
			return margin(mResources.getDimensionPixelSize(leftMarginId),
					mResources.getDimensionPixelSize(rightMarginId));
		}

		public Builder marginResId(@DimenRes int horizontalMarginId) {
			return marginResId(horizontalMarginId, horizontalMarginId);
		}

		public Builder marginProvider(MarginProvider provider) {
			mMarginProvider = provider;
			return this;
		}

		public RecHorizontalDividerItemDecoration build() {
			checkBuilderParams();
			return new RecHorizontalDividerItemDecoration(this);
		}
	}
}
