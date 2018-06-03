package com.laatonwalabhoot.hackernews.di.modules

import com.laatonwalabhoot.hackernews.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.logging.HttpLoggingInterceptor

@Module
class OkHttpLoggingInterceptorModule {

    @Provides
    @ApplicationScope
    fun okHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }
}