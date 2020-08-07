package com.vishesh.gallery.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Comment (
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    @ColumnInfo(name = "photo_id")
    val photoID: String?,
    @ColumnInfo(name = "text")
    val text: String?
)