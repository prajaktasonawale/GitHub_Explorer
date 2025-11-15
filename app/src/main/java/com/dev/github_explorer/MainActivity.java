package com.dev.github_explorer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView; // Correct import for standard SearchView
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.github_explorer.adapter.RepoAdapter;
import com.dev.github_explorer.listener.EndlessRecyclerViewScrollListener;
import com.dev.github_explorer.models.SearchViewModel;
import com.dev.github_explorer.models.Search_Result;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView; // This is now androidx.appcompat.widget.SearchView
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private RepoAdapter adapter;
    private SearchViewModel viewModel;

    private EndlessRecyclerViewScrollListener scrollListener;
    private String currentQuery = "";
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupSearchView();
        setupRecyclerView();
        setupViewModel();

        // Click → DetailActivity
        adapter.setOnItemClickListener(repo -> {
//            DetailActivity.start(MainActivity.this, repo);
            Intent Detail_Intent=new Intent(this,DetailActivity.class);
            Detail_Intent.putExtra(DetailActivity.EXTRA_REPO,repo);
            startActivity(Detail_Intent);
        });
    }

    private void initializeViews() {
        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recycler);
        progressBar = findViewById(R.id.progress);
    }

    private void setupSearchView() {
        // Set up search view listeners - this works with androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
           public boolean onQueryTextSubmit(String query) {
                // Handle search when user presses enter
                if (query != null && !query.trim().isEmpty()) {
                    doSearch(query.trim());
                    searchView.clearFocus(); // Hide keyboard
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Optional: Implement real-time search as user types
                // For now, we'll only search on submit
                return false;
            }
        });

        // Expand the search view by default (already done via app:iconifiedByDefault="false" in XML)
        // searchView.setIconified(false); // This line is optional since we set it in XML

        // Optional: Customize the search view
        customizeSearchView();
    }

    private void customizeSearchView() {
        try {
            // Change search icon color or behavior if needed
            int searchIconId = getResources().getIdentifier("android:id/search_mag_icon", null, null);
            View searchIcon = searchView.findViewById(searchIconId);
            if (searchIcon != null) {
                // You can customize the icon here if needed
                // searchIcon.setVisibility(View.GONE); // Uncomment to hide the icon
            }
        } catch (Exception e) {
            Log.d("SearchView", "Could not customize search icon");
        }
    }

    private void setupRecyclerView() {
        adapter = new RepoAdapter(new ArrayList<>(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (!currentQuery.isEmpty()) {
                    currentPage = page;
                    viewModel.search(currentQuery, currentPage);
                }
            }
        };

        recyclerView.addOnScrollListener(scrollListener);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        observeViewModel();
    }

    private void doSearch(String query) {
        if (query == null || query.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show();
            return;
        }

        currentQuery = query.trim();
        currentPage = 1;
        scrollListener.resetState();
        viewModel.search(currentQuery, currentPage);

        Log.d("SearchDebug", "Searching for: " + currentQuery);
    }

    private void observeViewModel() {
        viewModel.getSearchLive().observe(this, result -> {
            if (result == null) return;

            switch (result.status) {
                case LOADING:
                    if (currentPage == 1) {
                        progressBar.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                    break;

                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    Search_Result data = result.data;
                    Log.d("SearchDebug", "Data received: " + (data != null ? data.getItems().size() + " items" : "null"));

                    if (data != null && data.getItems() != null) {
                        if (currentPage == 1) {
                            adapter.setItems(data.getItems());
                            if (data.getItems().isEmpty()) {
                                Toast.makeText(this, "No repositories found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            adapter.addItems(data.getItems());
                        }
                    } else if (currentPage == 1) {
                        adapter.setItems(new ArrayList<>());
                        Toast.makeText(this, "No repositories found", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "Error: " + result.message, Toast.LENGTH_LONG).show();
                    Log.e("SearchError", "Search failed: " + result.message);
                    break;
            }
        });
    }
}