package com.glacierpower.data.di

import android.content.Context
import com.glacierpower.data.remote.RickAndMortyService
import com.glacierpower.data.repositoryImpl.RickAndMortyRepositoryImpl
import com.glacierpower.domain.RickAndMortyRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindRickAndMortyRepository(
        rickAndMortyRepositoryImpl: RickAndMortyRepositoryImpl
    ): RickAndMortyRepository

    companion object {

        private const val BASE_URL = "https://rickandmortyapi.com/api/"

        @Provides
        fun provideRickAndMortyService(retrofit: Retrofit): RickAndMortyService {
            return retrofit.create(RickAndMortyService::class.java)
        }

        @Provides
        fun provideBaseUrl(): String = BASE_URL

        @Provides
        fun provideRetrofit(
            baseUrl: String,
            client: OkHttpClient
        ): Retrofit {

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        @Provides
        fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        @Provides
        fun provideOkHttpClient(
            httpLoggingInterceptor: HttpLoggingInterceptor,
            context: Context
        ): OkHttpClient {
            val httpCacheDirectory = File(context.cacheDir, "http-cache")
            val cacheSize: Long = 10 * 1024 * 1024
            val cache = Cache(httpCacheDirectory, cacheSize)

            return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .callTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .cache(cache)
                .build()
        }
    }
}