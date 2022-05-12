package com.ristorante.ristoranteapp.presentation.home

import androidx.recyclerview.widget.DiffUtil
import com.ristorante.ristoranteapp.domain.home.News

class NewsDiffCallback : DiffUtil.ItemCallback<News>() {

    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean = oldItem == newItem
}