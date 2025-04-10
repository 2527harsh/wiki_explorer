package com.example.wikiexplorer.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL_MEDIAWIKI = "https://en.wikipedia.org/"
    private const val BASE_URL_REST = "https://en.wikipedia.org/api/rest_v1/"

    val mediaWikiApi: WikiApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_MEDIAWIKI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WikiApiService::class.java)
    }

    val restApi: WikiRestService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_REST)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WikiRestService::class.java)
    }
}
