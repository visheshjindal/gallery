package com.vishesh.gallery.data.response
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Data class for the Gallery Response Object which we receive from the api
 */
data class GalleryResponse (
    val id: String,
    val title: String?,
    @JsonProperty("is_album")
    val isAlbum: Boolean,
    val link: String,
    val images: List<ImageResponse>?
)