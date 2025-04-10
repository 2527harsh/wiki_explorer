package com.example.wikiexplorer.data.repository

import com.example.wikiexplorer.data.api.RetrofitInstance

class RandomRepository {
    // Use the mediaWikiApi for random articles
    suspend fun getRandomArticle() =
        RetrofitInstance.mediaWikiApi.getRandomArticle()
}
