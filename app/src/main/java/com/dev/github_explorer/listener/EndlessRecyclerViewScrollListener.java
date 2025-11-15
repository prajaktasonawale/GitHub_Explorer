package com.dev.github_explorer.listener;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager layoutManager;
    private int visibleThreshold = 5; // items remaining before loading more
    private int currentPage = 1;
    private int previousTotalItemCount = 0;
    private boolean loading = true;

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public void resetState() {
        this.currentPage = 1;
        this.previousTotalItemCount = 0;
        this.loading = true;
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int totalItemCount = layoutManager.getItemCount();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

        if (totalItemCount < previousTotalItemCount) {
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) this.loading = true;
        }

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            currentPage++;
            onLoadMore(currentPage, totalItemCount, view);
            loading = true;
        }
    }

    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);
}
