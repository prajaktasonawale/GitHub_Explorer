package com.dev.github_explorer.api_integration;

import com.dev.github_explorer.models.Search_Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("search/repositories")
    Call<Search_Result> searchRepos(@Query("q") String query,
                                    @Query("page") int page,
                                    @Query("per_page") int perPage);
}

