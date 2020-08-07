package com.vishesh.gallery.data.remote.services

import com.vishesh.gallery.data.response.CommonResponse
import com.vishesh.gallery.data.response.GalleryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GalleryService {

    @GET("3/gallery/search/{page}")
    suspend fun getGalleries(
        @Path("page") page: Int,
        @Query("q") query: String
    ): CommonResponse<GalleryResponse>

}