package com.dev.github_explorer.api_integration;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.concurrent.TimeUnit;

public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String BASE = "https://api.github.com/";

    public static ApiService getApi() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .callTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(chain -> {
                        Request original = chain.request();
                        Request.Builder builder = original.newBuilder()
                                .header("Accept", "application/vnd.github.v3+json");
                        // Optionally add token if you want to avoid very strict rate limits:
                        // builder.header("Authorization", "token YOUR_PERSONAL_ACCESS_TOKEN");
                        return chain.proceed(builder.build());
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}

