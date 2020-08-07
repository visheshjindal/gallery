package com.vishesh.gallery.data.response
import com.fasterxml.jackson.annotation.JsonProperty

data class GalleryResponse (
    val id: String,
    val title: String?,
    @JsonProperty("is_album")
    val isAlbum: Boolean,
    val link: String,
    val images: List<ImageResponse>?
)