package com.example.plantwiki.di

import com.example.plantwiki.Constants.Companion.BASE_URL
import com.example.plantwiki.api.PlantApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton


    @Module
    @InstallIn(SingletonComponent::class)
    object NetworkModule {
        @Provides
        @Singleton
        fun provideHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(
                    HttpLoggingInterceptor().apply{
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                .build()
        }
        @Singleton
        @Provides
        fun provideConverterFactory(): GsonConverterFactory {
            return GsonConverterFactory.create()
        }
        @Singleton
        @Provides
        fun provideRetrofitInstance(
            okHttpClient: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .build()
        }
        @Singleton
        @Provides
        fun provideService(retrofit: Retrofit): PlantApi {
            return retrofit.create(PlantApi::class.java)
        }

    }