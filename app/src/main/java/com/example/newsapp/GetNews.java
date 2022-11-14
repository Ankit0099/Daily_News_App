package com.example.newsapp;

import com.example.newsapp.Models.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface GetNews {

    @GET("/v2/top-headlines?country=in")
    Call<Response> getResponse(@Query("category") String category, @Query("apiKey") String apiKey);

}
