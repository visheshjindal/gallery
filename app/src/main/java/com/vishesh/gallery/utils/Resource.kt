package com.vishesh.gallery.utils

import com.vishesh.gallery.domain.entities.Photo

/**
 * Sealed class for the Gallery network response
 * which case Error and Success Response
 */
sealed class Resource {
    data class Error(val code: Int, val message: String?) : Resource()
    data class Success(val data: List<Photo>) : Resource()
}