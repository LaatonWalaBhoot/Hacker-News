package com.laatonwalabhoot.hackernews.di.modules

import com.laatonwalabhoot.hackernews.di.scopes.ApplicationScope
import com.laatonwalabhoot.hackernews.di.scopes.PerFragmentScope
import com.laatonwalabhoot.hackernews.ui.list.ListAdapter
import dagger.Module
import dagger.Provides

@Module
class ListAdapterModule {

    @Provides
    @PerFragmentScope
    fun listAdapter(): ListAdapter {
        return ListAdapter()
    }
}