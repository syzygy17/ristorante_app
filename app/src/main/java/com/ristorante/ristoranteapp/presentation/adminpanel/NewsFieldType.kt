package com.ristorante.ristoranteapp.presentation.adminpanel

sealed class NewsFieldType {
    data class Id(val id: Int) : NewsFieldType()
    data class ImageUrl(val imageUrl: String) : NewsFieldType()
    data class Title(val title: String) : NewsFieldType()
    data class Description(val description: String) : NewsFieldType()
}
