package com.ss.matchmakingapp.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Shubham Singhal
 */
public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

	LinearLayoutManager layoutManager;

	/**
	 * Constructor
	 *
	 * @param layoutManager
	 */
	public PaginationScrollListener(LinearLayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}

	@Override
	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
		super.onScrolled(recyclerView, dx, dy);

		int visibleItemCount = layoutManager.getChildCount();
		int totalItemCount = layoutManager.getItemCount();
		int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

		if (!isLoading() && !isLastPage()) {
			if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= getTotalPageCount()) {
				loadMoreItems();
			}
		}

	}

	/**
	 * Load more items on scroll.
	 */
	protected abstract void loadMoreItems();

	/**
	 * Get Total page count
	 *
	 * @return
	 */
	public abstract int getTotalPageCount();

	/**
	 * Check if page visible is the last page.
	 *
	 * @return
	 */
	public abstract boolean isLastPage();

	/**
	 * Check if next page is loading.
	 *
	 * @return
	 */
	public abstract boolean isLoading();
}
