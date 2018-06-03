package com.laatonwalabhoot.hackernews.di.modules

import com.laatonwalabhoot.hackernews.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module(includes = [OkHttpLoggingInterceptorModule::class])
class OkHttpClientModule {

    @Provides
    @ApplicationScope
    fun okHttpClient(okHttpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(okHttpLoggingInterceptor)
                .build()
    }
}