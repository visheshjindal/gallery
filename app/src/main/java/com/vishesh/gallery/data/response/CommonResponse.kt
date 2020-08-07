package com.vishesh.gallery.data.response

data class CommonResponse<out T> (
    val data: List<T>,
    val success: Boolean,
    val status: Int
)