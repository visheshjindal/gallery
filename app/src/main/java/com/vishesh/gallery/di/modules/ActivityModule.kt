package com.vishesh.gallery.di.modules

import com.vishesh.gallery.presentation.detail.DetailActivity
import com.vishesh.gallery.presentation.gallery.GalleryActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeGalleryActivity(): GalleryActivity

    @ContributesAndroidInjector
    abstract fun contributeDetailActivity(): DetailActivity
}