package com.ristorante.ristoranteapp.data.repository

import com.google.firebase.database.DatabaseReference
import com.ristorante.ristoranteapp.domain.Response
import com.ristorante.ristoranteapp.domain.adminpanel.AdminPanelRepository
import com.ristorante.ristoranteapp.domain.adminpanel.MutableMenu
import com.ristorante.ristoranteapp.domain.adminpanel.MutableNews
import kotlinx.coroutines.tasks.await

class DefaultAdminPanelRepository(
    private val database: DatabaseReference
) : AdminPanelRepository {

    override suspend fun saveNews(news: MutableNews): Response<Boolean> = try {
        val newsMap = mapOf<String, Any>(
            "imageUrl" to news.imageUrl,
            "title" to news.title,
            "description" to news.description
        )
        database.child("news").child(news.id.toString()).updateChildren(newsMap).await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }

    override suspend fun saveMenu(menu: MutableMenu): Response<Boolean> = try {
        val menuMap = mapOf<String, Any>(
            "imageUrl" to menu.imageUrl,
            "productName" to menu.productName,
            "productCost" to menu.productCost
        )
        database.child("menu").child(menu.id.toString()).updateChildren(menuMap).await()
        Response.Success(true)
    } catch (e: Exception) {
        Response.Failure(e)
    }
}