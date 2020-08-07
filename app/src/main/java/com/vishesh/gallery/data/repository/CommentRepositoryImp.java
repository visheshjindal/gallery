package com.vishesh.gallery.data.repository;

import androidx.lifecycle.LiveData;

import com.vishesh.gallery.data.local.CommentDatabase;
import com.vishesh.gallery.domain.entities.Comment;
import com.vishesh.gallery.domain.repository.CommentRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

/**
 * This repository is responsible for fetching and posting comments to the local database
 */
public class CommentRepositoryImp implements CommentRepository {

    private CommentDatabase commentDatabase;

    @Inject
    public CommentRepositoryImp(CommentDatabase commentDatabase) {
        this.commentDatabase = commentDatabase;
    }

    @NotNull
    @Override
    public LiveData<List<Comment>> getComments(@NotNull String photoID) {
        return commentDatabase.commentDao().loadAllComments(photoID);
    }

    @Override
    public void postComment(@NotNull Comment comment) {
        CommentDatabase.databaseWriteExecutor.submit(() -> commentDatabase.commentDao().insertAll(comment));
    }
}
