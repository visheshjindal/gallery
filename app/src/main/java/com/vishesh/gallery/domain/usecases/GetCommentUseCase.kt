package com.vishesh.gallery.domain.usecases

import androidx.lifecycle.LiveData
import com.vishesh.gallery.domain.entities.Comment
import com.vishesh.gallery.domain.repository.CommentRepository
import javax.inject.Inject

/**
 * An interactor that calls the actual implementation of [CommentRepository](which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */
class GetCommentUseCase @Inject constructor(private val commentRepository: CommentRepository) {

    fun postComment(comment: Comment) {
        commentRepository.postComment(comment)
    }

    fun getComments(photoID: String): LiveData<List<Comment>> =
        commentRepository.getComments(photoID)
}