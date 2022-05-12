package com.ristorante.ristoranteapp.presentation.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ristorante.ristoranteapp.R
import com.ristorante.ristoranteapp.domain.home.News

class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val newsImage = view.findViewById<ImageView>(R.id.news_image)
    private val newsTitle = view.findViewById<TextView>(R.id.news_title)

    fun bind(news: News) {
        newsImage.load(news.imageUrl)
        newsTitle.text = news.title
    }
}