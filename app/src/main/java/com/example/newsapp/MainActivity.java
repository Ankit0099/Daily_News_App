package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.newsapp.Models.ArticlesItem;
import com.example.newsapp.Models.Response;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Retrofit retrofit=null;
    GetNews getNews;
    private RecyclerView newsRecyclerView;
    private final String apiKey="486c7c896e0144838b78b4cd3f4e588e";
    private BottomNavigationView bottomNavigationView;
    private ProgressBar progressBar;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit=setRetrofit();
        newsRecyclerView=findViewById(R.id.newsRecyclerView);
        progressBar=findViewById(R.id.progressBar);
        actionBar = getSupportActionBar();
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        newsRecyclerView.setHasFixedSize(true);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        actionBar.setTitle("General");
        getNews("general",apiKey);



    }

    private void getNews(String category,String apiKey) {
        actionBar.setTitle(Character.toUpperCase(category.charAt(0))+category.substring(1));
        progressBar.setVisibility(View.VISIBLE);
        newsRecyclerView.setVisibility(View.INVISIBLE);
        getNews=retrofit.create(GetNews.class);
        Call<Response> responseCall=getNews.getResponse(category,apiKey);

        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                List<ArticlesItem> articlesItemList=response.body().getArticles();

                for(ArticlesItem articlesItem:articlesItemList){
                    Log.i("TAG",articlesItem.getTitle()+"----->"+articlesItem.getAuthor());
                }

                NewsAdapter adapter=new NewsAdapter(MainActivity.this,articlesItemList);
                adapter.notifyDataSetChanged();
                newsRecyclerView.setAdapter(adapter);
                newsRecyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                newsRecyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    private Retrofit setRetrofit() {
        if(retrofit==null)
        retrofit=new Retrofit.Builder().baseUrl("https://newsapi.org/").addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.general:

                getNews("general",apiKey);
                return true;
            case R.id.business:
                getNews("business",apiKey);
                return true;
            case R.id.entertainment:
                getNews("entertainment",apiKey);
                return true;
            case R.id.tech:
                getNews("technology",apiKey);
                return true;
            case R.id.sports:
                getNews("sports",apiKey);
                return true;

            default:
                return false;

        }
    }
}