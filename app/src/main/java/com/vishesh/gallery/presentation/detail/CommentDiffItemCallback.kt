package com.vishesh.gallery.presentation.detail

import androidx.recyclerview.widget.DiffUtil
import com.vishesh.gallery.domain.entities.Comment

/**
 * DiffItemCallback for the Comment data class which provides diff for the adapter
 */
class CommentDiffItemCallback: DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.uid == newItem.uid
                && oldItem.photoID == newItem.photoID
                && oldItem.text == newItem.text
    }

}