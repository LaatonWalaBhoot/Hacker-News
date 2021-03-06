package com.laatonwalabhoot.hackernews.di.modules

import com.laatonwalabhoot.hackernews.common.Constants
import com.laatonwalabhoot.hackernews.data.remote.ApiService
import com.laatonwalabhoot.hackernews.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [(OkHttpClientModule::class)])
class ApiServiceModule {

    @Provides
    @ApplicationScope
    fun apiService(okHttpClient: OkHttpClient): ApiService {

        return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build().create(ApiService::class.java)
    }
}