package com.ristorante.ristoranteapp.presentation.menu

import androidx.recyclerview.widget.DiffUtil
import com.ristorante.ristoranteapp.domain.menu.Menu

class MenuDiffCallback : DiffUtil.ItemCallback<Menu>() {
    override fun areItemsTheSame(oldItem: Menu, newItem: Menu): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Menu, newItem: Menu): Boolean = oldItem == newItem
}