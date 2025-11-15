package com.dev.github_explorer;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dev.github_explorer.api_integration.RepoRepository;
import com.dev.github_explorer.helper.Result;
import com.dev.github_explorer.models.Search_Result;

public class SearchViewModel extends ViewModel {
    private RepoRepository repository = new RepoRepository();
    private MutableLiveData<Result<Search_Result>> searchLive = new MutableLiveData<>();

    public LiveData<Result<Search_Result>> getSearchLive() {
        return searchLive;
    }

    public void search(String q, int page) {
        repository.search(q, page, 30, searchLive);
    }
}
