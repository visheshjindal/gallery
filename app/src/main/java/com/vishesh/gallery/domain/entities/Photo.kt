package com.vishesh.gallery.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    val id: String,
    val link: String,
    val title: String?
): Parcelable