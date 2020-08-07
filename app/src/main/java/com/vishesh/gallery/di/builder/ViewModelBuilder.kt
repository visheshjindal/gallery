package com.vishesh.gallery.di.builder

import androidx.lifecycle.ViewModel
import com.vishesh.gallery.di.ViewModelKey
import com.vishesh.gallery.presentation.detail.DetailViewModel
import com.vishesh.gallery.presentation.gallery.GalleryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsBuilder {

    @Binds
    @IntoMap
    @ViewModelKey(GalleryViewModel::class)
    abstract fun galleryViewModel(galleryViewModel: GalleryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun detailViewModel(detailViewModel: DetailViewModel): ViewModel

}