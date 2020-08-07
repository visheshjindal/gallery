package com.vishesh.gallery

import android.app.Application
import com.vishesh.gallery.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject
import kotlin.properties.Delegates

class MainApplication : Application(), HasAndroidInjector {

    private val TAG = "MainApplication"
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }


    companion object {
        var instance: MainApplication by Delegates.notNull()
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}