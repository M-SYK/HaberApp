package com.mahsunsayak.haberapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahsunsayak.haberapp.databinding.ListItemNewsBinding
import com.mahsunsayak.haberapp.model.News

class NewsListAdapter(private val newsList: List<News>, private val onClick: (News) -> Unit) : RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(val binding: ListItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.newsTitleTextView.text = news.title
            Glide.with(binding.root)
                .load(news.urlToImage)
                .into(binding.newsImageView)

            itemView.setOnClickListener { onClick(news) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ListItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.bind(news)
    }

    override fun getItemCount() = newsList.size
}
