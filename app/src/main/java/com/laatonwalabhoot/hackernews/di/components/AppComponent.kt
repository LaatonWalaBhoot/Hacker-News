package com.laatonwalabhoot.hackernews.di.components

import com.google.gson.Gson
import com.laatonwalabhoot.hackernews.data.remote.ApiService
import com.laatonwalabhoot.hackernews.di.modules.ApiServiceModule
import com.laatonwalabhoot.hackernews.di.modules.GsonModule
import com.laatonwalabhoot.hackernews.di.scopes.ApplicationScope
import dagger.Component

@Component(modules = [ApiServiceModule::class, GsonModule::class])
@ApplicationScope
interface AppComponent {

    fun apiService(): ApiService

    fun gson(): Gson
}