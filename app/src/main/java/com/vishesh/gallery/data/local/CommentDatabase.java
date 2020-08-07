package com.vishesh.gallery.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.vishesh.gallery.data.local.dao.CommentDao;
import com.vishesh.gallery.domain.entities.Comment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Comment.class}, version = 1, exportSchema = false)
public abstract class CommentDatabase extends RoomDatabase {

    public abstract CommentDao commentDao();
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static final String DB_NAME = "comment_database";
}