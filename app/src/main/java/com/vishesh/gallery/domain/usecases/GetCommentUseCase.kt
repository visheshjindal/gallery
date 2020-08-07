package com.vishesh.gallery.domain.usecases

import androidx.lifecycle.LiveData
import com.vishesh.gallery.domain.entities.Comment
import com.vishesh.gallery.domain.repository.CommentRepository

class GetCommentUseCase(private val commentRepository: CommentRepository) {

    fun postComment(comment: Comment) {
        commentRepository.postComment(comment)
    }

    fun getComments(photoID: String): LiveData<List<Comment>> = commentRepository.getComments(photoID)

}