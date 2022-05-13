package com.ristorante.ristoranteapp.presentation.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ristorante.ristoranteapp.R
import com.ristorante.ristoranteapp.domain.menu.Menu

class MenuAdapter(
    private val onItemClick: (menu: Menu) -> Unit
) : ListAdapter<Menu, MenuAdapter.MenuViewHolder>(MenuDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder =
        MenuViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        )

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val menuImage = view.findViewById<ImageView>(R.id.menu_image)
        private val productName = view.findViewById<TextView>(R.id.product_name)
        private val productCost = view.findViewById<TextView>(R.id.product_cost)
        private val toBasketButton = view.findViewById<Button>(R.id.to_basket_button)

        fun bind(menu: Menu) {
            menuImage.load(menu.imageUrl)
            productName.text = menu.productName
            productCost.text = menu.productCost
            toBasketButton.setOnClickListener {
                onItemClick(menu)
            }
        }
    }
}
