package com.dev.github_explorer.api_integration;

import androidx.annotation.NonNull;

import com.dev.github_explorer.api_integration.ApiClient;
import com.dev.github_explorer.api_integration.ApiService;
import com.dev.github_explorer.models.Search_Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GitHubRepository {

    private final ApiService api;

    public GitHubRepository() {
        api = ApiClient.getClient().create(ApiService.class);
    }

    public interface SearchCallback {
        void onSuccess(Search_Result result);
        void onError(String error);
    }

    public void search(String query, int page, SearchCallback callback) {
        api.searchRepos(query, page, 30).enqueue(new Callback<Search_Result>() {
            @Override
            public void onResponse(@NonNull Call<Search_Result> call,
                                   @NonNull Response<Search_Result> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("API Error: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Search_Result> call, @NonNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
