package com.vishesh.gallery.presentation.detail

import androidx.recyclerview.widget.DiffUtil
import com.vishesh.gallery.domain.entities.Comment

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