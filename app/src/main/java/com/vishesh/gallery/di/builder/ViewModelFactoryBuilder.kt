package com.vishesh.gallery.di.builder

import com.vishesh.gallery.di.ViewModelFactory
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module(includes = [(ViewModelsBuilder::class)])
abstract class ViewModelFactoryBuilder {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}