package com.ristorante.ristoranteapp.domain.adminpanel

data class MutableMenu(
    var id: Int = -1,
    var imageUrl: String = "",
    var productName: String = "",
    var productCost: String = ""
)