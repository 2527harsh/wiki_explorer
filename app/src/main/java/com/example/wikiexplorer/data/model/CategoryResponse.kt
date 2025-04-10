package com.example.wikiexplorer.data.model

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    val query: CategoryQuery?,
    @SerializedName("continue")
    val continueToken: Map<String, String>? = null
)

data class CategoryQuery(
    val allcategories: List<CategoryItem>
)

data class CategoryItem(
    @SerializedName("*") // Handles the asterisk key in JSON
    val category: String
)
