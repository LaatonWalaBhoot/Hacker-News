package com.laatonwalabhoot.hackernews.di.components

import com.laatonwalabhoot.hackernews.data.remote.ApiClient
import com.laatonwalabhoot.hackernews.di.modules.RetrofitModule
import com.laatonwalabhoot.hackernews.di.scopes.ApplicationScope
import dagger.Component

@Component(modules = [RetrofitModule::class])
@ApplicationScope
interface ApiClientComponent {

    fun injectApiClient(apiClient: ApiClient)
}