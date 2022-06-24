package com.example.sharedapi

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.ContentType.Application.Json
import io.ktor.util.Identity.decode
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GalleryApi {


    private val apiUrl = "https://picsum.photos/v2/list?"

    fun getGalleryList(
        page: Int = 1, limit: Int = 20,
        success: (List<ApiGalleryItem>) -> Unit, failure: (Throwable?) -> Unit
    ) {


        GlobalScope.launch(ApplicationDispatcher) {
            try {
                val url = "${apiUrl}page=$page&limit=$limit"
                // 6
                val json = HttpClient().get<String>(url)
                // 7
                val wrappedStringJson = """
                {
                    "images": $json
                }
                """.trimIndent()
                kotlinx.serialization.json.Json.decodeFromString(
                    GalleryList.serializer(),
                    wrappedStringJson
                )
                    .images
                    .also(success)
            } catch (ex: Exception) {
                failure(ex)
            }
        }

    }


}