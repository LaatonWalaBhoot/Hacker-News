package com.laatonwalabhoot.hackernews.data.remote

import com.laatonwalabhoot.hackernews.di.components.ApiClientComponent
import com.laatonwalabhoot.hackernews.di.components.DaggerApiClientComponent
import retrofit2.Retrofit
import javax.inject.Inject

class ApiClient {

    @Inject
    lateinit var retrofit: Retrofit

    private lateinit var apiClientComponent: ApiClientComponent
    private lateinit var apiService: ApiService

    companion object {
        private val apiClient: ApiClient = ApiClient()

        @Synchronized
        fun getInstance(): ApiClient {
            apiClient.init()
            return apiClient
        }
    }

    /************************************
     * PUBLIC METHODS
     ************************************/
    fun init() {
        apiClientComponent = DaggerApiClientComponent.builder().build()
        apiClientComponent.injectApiClient(this)
        apiService = retrofit.create(ApiService::class.java)
    }

    fun getApiService(): ApiService {
        /*
         * Aishwarya: API will be made from the view model to
         * persist with the single responsibility principle
         */
        return apiService
    }
}