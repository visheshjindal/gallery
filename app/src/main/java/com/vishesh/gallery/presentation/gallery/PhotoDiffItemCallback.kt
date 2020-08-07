package com.vishesh.gallery.presentation.gallery

import androidx.recyclerview.widget.DiffUtil
import com.vishesh.gallery.domain.entities.Photo

class PhotoDiffItemCallback: DiffUtil.ItemCallback<Photo?>() {

    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
                && oldItem.link == newItem.id
                && oldItem.title == newItem.title
    }
}