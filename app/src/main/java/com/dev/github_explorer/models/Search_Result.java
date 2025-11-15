package com.dev.github_explorer.models;

import java.util.List;



public class Search_Result {
    private int total_count;
    private boolean incomplete_results;
    private List<Repository> items;

    public int getTotal_count() { return total_count; }
    public boolean isIncomplete_results() { return incomplete_results; }
    public List<Repository> getItems() { return items; }
}
