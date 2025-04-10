package com.example.wikiexplorer.data.model

data class FeaturedImageResponse(
    val tfa: Picture?
)

data class Picture(
    val title: String?,
    val description: String?,
    val original: ImageInfo?,
    val thumbnail: ImageInfo?
)

data class ImageInfo(
    val source: String?,
    val width: Int?,
    val height: Int?
)
