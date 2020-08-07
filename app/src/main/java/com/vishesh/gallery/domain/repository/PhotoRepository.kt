package com.vishesh.gallery.domain.repository

import com.vishesh.gallery.utils.Resource

/**
 * To make an interaction between [PhotoRepositoryImpl] & [GetPhotosUseCase]
 * */
interface PhotoRepository {

    /**
     * Fetch photos from the database or the remote source
     * @param page: Pagination count
     * @param query: For which the photos needs to fetched
     */
    suspend fun getPhotos(page: Int = 1, query: String): Resource
}