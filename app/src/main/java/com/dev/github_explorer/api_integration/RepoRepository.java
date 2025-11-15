package com.dev.github_explorer.api_integration;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.dev.github_explorer.helper.Result;
import com.dev.github_explorer.models.Search_Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RepoRepository {
    private final ApiService api = RetrofitClient.getApi();

    public void search(String q, int page, int i, MutableLiveData<Result<Search_Result>> liveData) {
        liveData.postValue(Result.loading());
        api.searchRepos(q, page, 30).enqueue(new Callback<Search_Result>() {
            @Override
            public void onResponse(Call<Search_Result> call, Response<Search_Result> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(Result.success(response.body()));
                } else {
                    String err = "HTTP " + response.code();
                    liveData.postValue(Result.error(err));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Search_Result> call, @NonNull Throwable t) {
                liveData.postValue(Result.error(t.getMessage()));
            }
        });
    }
}
