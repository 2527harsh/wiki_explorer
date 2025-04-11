package com.example.wikiexplorer.data.repository

import com.example.wikiexplorer.data.api.RetrofitInstance

class RandomRepository {
    suspend fun getRandomArticle() = RetrofitInstance.mediaWikiApi.getRandomArticle()
}
