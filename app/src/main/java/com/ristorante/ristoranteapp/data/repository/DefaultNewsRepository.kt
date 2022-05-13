package com.ristorante.ristoranteapp.data.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import com.ristorante.ristoranteapp.domain.Response
import com.ristorante.ristoranteapp.domain.home.NewsRepository
import com.ristorante.ristoranteapp.domain.home.News
import kotlinx.coroutines.tasks.await

class DefaultNewsRepository(
    private val database: DatabaseReference
) : NewsRepository {

    override suspend fun getNews(): Response<List<News>> = try {
        val task = database.child("news").get().await()
        if (task.exists()) {
            val newsList = mutableListOf<News>()
            task.children.forEach {
                newsList.add(it.getValue<News>() ?: return@forEach)
            }
            Response.Success(newsList)
        } else {
            Response.Failure(Throwable())
        }
    } catch (e: Exception) {
        Response.Failure(e)
    }
}
