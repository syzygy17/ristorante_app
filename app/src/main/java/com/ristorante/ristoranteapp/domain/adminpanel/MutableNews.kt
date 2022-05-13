package com.ristorante.ristoranteapp.domain.adminpanel

data class MutableNews(
    var id: Int = -1,
    var imageUrl: String = "",
    var title: String = "",
    var description: String = ""
)
