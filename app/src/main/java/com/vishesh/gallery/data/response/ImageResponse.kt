package com.vishesh.gallery.data.response

/**
 * Data class for the ImageResponse if is_album is true we get this
 * in the images array
 */
data class ImageResponse (
    val id: String,
    val link: String,
    val title: String?
)