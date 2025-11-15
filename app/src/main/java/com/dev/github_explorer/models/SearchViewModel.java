package com.dev.github_explorer.models;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dev.github_explorer.api_integration.GitHubRepository;
import com.dev.github_explorer.helper.Resource;

public class SearchViewModel extends ViewModel {

    private final MutableLiveData<Resource<Search_Result>> searchLive = new MutableLiveData<>();
    private final GitHubRepository repository = new GitHubRepository();

    public LiveData<Resource<Search_Result>> getSearchLive() {
        return searchLive;
    }

    public void search(String query, int page) {
        searchLive.setValue(Resource.loading(null));

        repository.search(query, page, new GitHubRepository.SearchCallback() {
            @Override
            public void onSuccess(Search_Result result) {
                searchLive.postValue(Resource.success(result));
            }

            @Override
            public void onError(String error) {
                searchLive.postValue(Resource.error(error, null));
            }
        });
    }
}


