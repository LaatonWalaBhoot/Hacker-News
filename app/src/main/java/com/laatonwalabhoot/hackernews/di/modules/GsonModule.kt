package com.laatonwalabhoot.hackernews.di.modules

import com.google.gson.Gson
import com.laatonwalabhoot.hackernews.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class GsonModule {

    @Provides
    @ApplicationScope
    fun gson(): Gson {
        return Gson()
    }
}