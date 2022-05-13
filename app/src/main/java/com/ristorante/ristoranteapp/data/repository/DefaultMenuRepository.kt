package com.ristorante.ristoranteapp.data.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import com.ristorante.ristoranteapp.domain.Response
import com.ristorante.ristoranteapp.domain.menu.Menu
import com.ristorante.ristoranteapp.domain.menu.MenuRepository
import kotlinx.coroutines.tasks.await

class DefaultMenuRepository(
    private val database: DatabaseReference
) : MenuRepository {

    override suspend fun getMenu(): Response<List<Menu>> = try {
        val task = database.child("menu").get().await()
        if (task.exists()) {
            val menuList = mutableListOf<Menu>()
            task.children.forEach {
                menuList.add(it.getValue<Menu>() ?: return@forEach)
            }
            Response.Success(menuList)
        } else {
            Response.Failure(Throwable())
        }
    } catch (e: Exception) {
        Response.Failure(e)
    }
}