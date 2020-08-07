package com.vishesh.gallery.data.response

/**
 * Generic response class to handle network response
 */
data class CommonResponse<out T> (
    val data: List<T>,
    val success: Boolean,
    val status: Int
)