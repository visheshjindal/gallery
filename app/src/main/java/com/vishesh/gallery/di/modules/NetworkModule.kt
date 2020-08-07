package com.vishesh.gallery.di.modules

import android.content.Context
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.vishesh.gallery.data.remote.services.GalleryService
import com.vishesh.gallery.data.repository.PhotoRepositoryImpl
import com.vishesh.gallery.domain.repository.PhotoRepository
import com.vishesh.gallery.utils.BASE_URL
import com.vishesh.gallery.utils.CLIENT_ID
import com.vishesh.gallery.utils.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ApplicationModule::class])
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(
        jacksonConverterFactory: JacksonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(jacksonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(context: Context): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
        client.addInterceptor(NetworkConnectionInterceptor(context))
        client.addInterceptor(interceptor)
        client.addInterceptor { chain ->
            val request = prepareAuth(chain)
            chain.proceed(request)
        }

        return client.build()
    }

    private fun prepareAuth(it: Interceptor.Chain): Request {
        val original = it.request()
        return original
            .newBuilder()
            .header("Authorization", CLIENT_ID)
            .build()
    }

    @Provides
    @Singleton
    fun providesJacksonConverterFactory(): JacksonConverterFactory {
       val mapper = ObjectMapper().registerModule(KotlinModule())
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return JacksonConverterFactory.create(mapper)
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): GalleryService {
        return retrofit.create(GalleryService::class.java)
    }

    @Singleton
    @Provides
    fun provideAlbumRepository(galleryService: GalleryService): PhotoRepository {
        return PhotoRepositoryImpl(galleryService)
    }

}