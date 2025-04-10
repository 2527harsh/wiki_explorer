package com.example.wikiexplorer.data.api

import com.example.wikiexplorer.data.model.FeaturedImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WikiRestService {

    // Featured image endpoint – note that the URL is constructed from the BASE_URL_REST defined in RetrofitInstance
    @GET("feed/featured/{year}/{month}/{day}")
    suspend fun getFeaturedImages(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Path("day") day: Int
    ): Response<FeaturedImageResponse>
}
