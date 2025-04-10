package com.example.wikiexplorer.data.api

import com.example.wikiexplorer.data.model.CategoryResponse
import com.example.wikiexplorer.data.model.RandomArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WikiApiService {

    // Random article endpoint
    @GET("w/api.php?action=query&format=json&generator=random&grnnamespace=0&prop=extracts&exintro=true&explaintext=true")
    suspend fun getRandomArticle(): Response<RandomArticleResponse>

    // Categories endpoint
    @GET("w/api.php?action=query&format=json&list=allcategories&aclimit=50")
    suspend fun getCategories(
        @Query("accontinue") continueToken: String? = null
    ): Response<CategoryResponse>
}
