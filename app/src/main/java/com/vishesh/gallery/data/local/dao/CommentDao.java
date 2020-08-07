package com.vishesh.gallery.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.vishesh.gallery.domain.entities.Comment;

import java.util.List;

@Dao
public interface CommentDao {
    @Query("SELECT * FROM Comment")
    List<Comment> getAll();

    @Query("SELECT * FROM Comment WHERE photo_id = :photoID ORDER BY uid desc")
    LiveData<List<Comment>> loadAllComments(String photoID);

    @Insert
    void insertAll(Comment... comments);
}
