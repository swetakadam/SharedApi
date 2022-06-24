package com.example.sharedapi
import kotlinx.serialization.Serializable

data class GalleryLoadRequest(
    val page:Int,
    val limit:Int = 10
)

@Serializable
data class ApiGalleryItem(
    val id: Int,
    val author: String?,
    val width: Int,
    val height: Int,
    val url: String,
    val download_url: String
)

@Serializable
data class GalleryList(val images:List<ApiGalleryItem>)
