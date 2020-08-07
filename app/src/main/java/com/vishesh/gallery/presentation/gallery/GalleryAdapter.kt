package com.vishesh.gallery.presentation.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vishesh.gallery.R
import com.vishesh.gallery.databinding.ItemImageViewBinding
import com.vishesh.gallery.databinding.ItemLoadingBinding
import com.vishesh.gallery.domain.entities.Photo

class GalleryAdapter : ListAdapter<Photo?, RecyclerView.ViewHolder>(PhotoDiffItemCallback()) {

    companion object {
         const val imageItem = 0
        const val loadingItem = 1
    }

    var onItemClick: ((imageView: AppCompatImageView, item: Photo) -> Unit)? = null

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == null) loadingItem else imageItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == loadingItem) {
            LoadingViewHolder.create(parent)
        } else {
            ViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val item = getItem(position)
            holder.binding.root.setOnClickListener {
                item?.let { onItemClick?.invoke(holder.binding.imageView, it) }
            }
            ViewCompat.setTransitionName(holder.binding.imageView, item?.id)
            Glide.with(holder.binding.root)
                .load(item?.link)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .optionalCenterCrop()
                .into(holder.binding.imageView)
        }
    }

    class ViewHolder(val binding: ItemImageViewBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val binding = ItemImageViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ViewHolder(
                    binding
                )
            }
        }
    }

    class LoadingViewHolder(binding: ItemLoadingBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): LoadingViewHolder {
                val binding = ItemLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return LoadingViewHolder(
                    binding
                )
            }
        }
    }
}