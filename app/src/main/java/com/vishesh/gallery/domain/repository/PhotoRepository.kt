package com.vishesh.gallery.domain.repository

import com.vishesh.gallery.utils.Resource

/**
 * To make an interaction between [PhotoRepositoryImpl] & [GetPhotosUseCase]
 * */
interface PhotoRepository {

    suspend fun getPhotos(page: Int = 1, query: String): Resource
}