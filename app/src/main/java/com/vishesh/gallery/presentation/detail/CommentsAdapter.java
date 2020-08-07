package com.vishesh.gallery.presentation.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.vishesh.gallery.databinding.ItemCommentBinding;
import com.vishesh.gallery.domain.entities.Comment;

public class CommentsAdapter extends ListAdapter<Comment, CommentsAdapter.ViewHolder> {

    protected CommentsAdapter(@NonNull DiffUtil.ItemCallback<Comment> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCommentBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder holder, int position) {
        Comment comment = getItem(position);
        holder.binding.tvComment.setText(comment.getText());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCommentBinding binding;

        public ViewHolder(@NonNull ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
