package com.example.wikiexplorer.data.model

data class RandomArticleResponse(
    val query: Query?
)

data class Query(
    val pages: Map<String, Page>
)
