package com.vishesh.gallery.data.repository

import com.vishesh.gallery.utils.Resource
import com.vishesh.gallery.utils.ResponseHandler
import com.vishesh.gallery.data.remote.services.GalleryService
import com.vishesh.gallery.data.response.GalleryResponse
import com.vishesh.gallery.domain.entities.Photo
import com.vishesh.gallery.domain.repository.PhotoRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async

/**
 * This repository is responsible for fetching from the Provided API
 */
class PhotoRepositoryImpl(private val galleryService: GalleryService): PhotoRepository {

    private val responseHandler = ResponseHandler()

    override suspend fun getPhotos(page: Int, query: String): Resource {
        return try {
            val response = galleryService.getGalleries(page, query)
            val photoList = mapToPhotosAsync(response.data).await()
            responseHandler.handleSuccess(photoList)
        } catch (ex: Exception) {
            responseHandler.handleException(ex)
        }
    }

    /**
     * Map the gallery response to the required Photo list asynchronously, It will flat the gallery
     * album photos to a linear list in case of any gallery containing album else it will append the
     * gallery photo link as Photo
     * @param galleries: List of received Galleries from the api
     */
    private fun mapToPhotosAsync(galleries: List<GalleryResponse>) = GlobalScope.async {
        galleries.flatMap { gallery ->
            if (gallery.isAlbum) {
                gallery.images?.map {
                    Photo(
                        it.id,
                        it.link,
                        gallery.title
                    )
                } ?: emptyList()
            } else {
                listOf(
                    Photo(
                        gallery.id,
                        gallery.link,
                        gallery.title
                    )
                )
            }
        }
    }

}