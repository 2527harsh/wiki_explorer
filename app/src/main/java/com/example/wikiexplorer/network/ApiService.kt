package com.example.wikiexplorer.network

import com.example.wikiexplorer.data.model.CategoryResponse
import com.example.wikiexplorer.data.model.FeaturedImageResponse
import com.example.wikiexplorer.data.model.RandomArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("w/api.php?action=query&list=allcategories&format=json&aclimit=50")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("w/api.php?action=query&format=json&generator=random&grnnamespace=0&prop=extracts&exintro=true&explaintext=true")
    suspend fun getRandomArticle(): Response<RandomArticleResponse>

    @GET("feed/featured/{year}/{month}/{day}")
    suspend fun getFeaturedImages(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Path("day") day: Int
    ): Response<FeaturedImageResponse>

    @GET("w/api.php?action=query&list=allcategories&format=json&aclimit=50")
    suspend fun getCategories(
        @Query("accontinue") continueToken: String? = null
    ): Response<CategoryResponse>

}
