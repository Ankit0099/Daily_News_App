package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.gesture.GestureLibraries;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.Models.ArticlesItem;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    Context context;
    List<ArticlesItem> articlesItemList;

    public NewsAdapter(Context context, List<ArticlesItem> articlesItemList) {
        this.context = context;
        this.articlesItemList = articlesItemList;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.news_item,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull NewsAdapter.NewsViewHolder holder, int position) {
        ArticlesItem articlesItem=articlesItemList.get(position);
        holder.newsItemTitle.setText(articlesItem.getTitle());
        holder.newsItemDesc.setText(articlesItem.getDescription());

        String imgUrl=articlesItem.getUrlToImage();
        if(imgUrl!=null && imgUrl.length()!=0){
            Glide.with(context).load(imgUrl).into(holder.newsItemImage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(articlesItem.getUrl()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return articlesItemList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView newsItemImage;
        TextView newsItemTitle,newsItemDesc;

        public NewsViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            newsItemImage=itemView.findViewById(R.id.newsItemImage);
            newsItemDesc=itemView.findViewById(R.id.newsItemDesc);
            newsItemTitle=itemView.findViewById(R.id.newsItemTitle);
        }
    }
}
