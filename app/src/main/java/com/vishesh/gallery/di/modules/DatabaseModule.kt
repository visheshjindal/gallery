package com.vishesh.gallery.di.modules

import android.app.Application
import androidx.room.Room
import com.vishesh.gallery.data.local.CommentDatabase
import com.vishesh.gallery.data.local.dao.CommentDao
import com.vishesh.gallery.data.remote.services.GalleryService
import com.vishesh.gallery.data.repository.CommentRepositoryImp
import com.vishesh.gallery.data.repository.PhotoRepositoryImpl
import com.vishesh.gallery.domain.repository.CommentRepository
import com.vishesh.gallery.domain.repository.PhotoRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(application: Application): CommentDatabase {
        return Room.databaseBuilder(
            application,
            CommentDatabase::class.java,
            CommentDatabase.DB_NAME
        ).allowMainThreadQueries().build()
    }


    @Provides
    internal fun provideCommentDao(appDatabase: CommentDatabase): CommentDao {
        return appDatabase.commentDao()
    }

    @Singleton
    @Provides
    fun provideCommentRepository(commentDatabase: CommentDatabase): CommentRepository {
        return CommentRepositoryImp(commentDatabase)
    }
}