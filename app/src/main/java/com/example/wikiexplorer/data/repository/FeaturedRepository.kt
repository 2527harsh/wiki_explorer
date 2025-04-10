package com.example.wikiexplorer.data.repository

import com.example.wikiexplorer.data.api.RetrofitInstance

class FeaturedRepository {
    // Use restApi for featured images endpoint
    suspend fun getFeaturedImages(year: Int, month: Int, day: Int) =
        RetrofitInstance.restApi.getFeaturedImages(year, month, day)
}
