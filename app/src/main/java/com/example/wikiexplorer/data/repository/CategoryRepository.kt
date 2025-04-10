package com.example.wikiexplorer.data.repository

import android.content.Context
import com.example.wikiexplorer.data.api.RetrofitInstance
import com.example.wikiexplorer.data.db.CategoryDatabase
import com.example.wikiexplorer.data.db.CategoryEntity

class CategoryRepository(private val context: Context) {

    private val dao = CategoryDatabase.getDatabase(context).categoryDao()
    // Use the mediaWikiApi to fetch categories (action query API)
    private val api = RetrofitInstance.mediaWikiApi

    suspend fun fetchCategoriesFromApi(continueToken: String? = null): Pair<List<CategoryEntity>, String?> {
        val response = api.getCategories(continueToken)

        if (response.isSuccessful) {
            val categories = response.body()?.query?.allcategories ?: emptyList()
            val nextToken = response.body()?.continueToken?.get("accontinue")

            // Map API CategoryItem to your local CategoryEntity
            val categoryList = categories.map { CategoryEntity(category = it.category) }

            // Clear previous data if not loading more pages
            if (continueToken == null) dao.clear()
            dao.insertAll(categoryList)

            return categoryList to nextToken
        } else {
            throw Exception("API Error: ${response.code()} ${response.message()}")
        }
    }

    suspend fun getCategoriesFromDb(): List<CategoryEntity> {
        return dao.getAll()
    }
}
