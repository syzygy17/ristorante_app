package com.ristorante.ristoranteapp.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ristorante.ristoranteapp.R
import com.ristorante.ristoranteapp.domain.home.News

class NewsAdapter(
    private val onItemClick: (news: News) -> Unit
) : ListAdapter<News, NewsAdapter.NewsViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val newsImage = view.findViewById<ImageView>(R.id.news_image)
        private val newsTitle = view.findViewById<TextView>(R.id.news_title)

        fun bind(news: News) {
            newsImage.load(news.imageUrl)
            newsTitle.text = news.title
            itemView.setOnClickListener {
                onItemClick(news)
            }
        }
    }
}