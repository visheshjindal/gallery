package com.vishesh.gallery.domain.usecases

import com.vishesh.gallery.utils.Resource
import com.vishesh.gallery.domain.repository.PhotoRepository
import javax.inject.Inject

/**
 * An interactor that calls the actual implementation of [PhotosViewModel](which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */
class GetPhotosUseCase @Inject constructor(private val repository: PhotoRepository) {

    suspend fun getPhotos(page: Int, query: String): Resource {
        return repository.getPhotos(page, query)
    }
}