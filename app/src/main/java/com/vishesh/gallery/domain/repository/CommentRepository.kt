package com.vishesh.gallery.domain.repository

import androidx.lifecycle.LiveData
import com.vishesh.gallery.domain.entities.Comment

/**
 * To make an interaction between [CommentRepositoryImp] & [GetPhotosUseCase]
 * */
interface CommentRepository {

    /**
     * Get comments for the provided photo id which is unique for every photo
     * @param photoID: String photo id
     * @return LiveData<List<Comments>>: Return a Live data list of Comments found
     */
    fun getComments(photoID: String): LiveData<List<Comment>>

    /**
     * Save comment locally in database with the unique photo id
     * @param comment: Comment class with comment text and unique with photo id
     */
    fun postComment(comment: Comment)
}