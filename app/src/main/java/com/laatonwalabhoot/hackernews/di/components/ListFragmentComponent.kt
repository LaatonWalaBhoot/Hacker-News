package com.laatonwalabhoot.hackernews.di.components

import com.laatonwalabhoot.hackernews.di.modules.ApiServiceModule
import com.laatonwalabhoot.hackernews.di.modules.ListAdapterModule
import com.laatonwalabhoot.hackernews.di.scopes.ApplicationScope
import com.laatonwalabhoot.hackernews.di.scopes.PerFragmentScope
import com.laatonwalabhoot.hackernews.ui.list.ListFragment
import dagger.Component

@Component(modules = [ListAdapterModule::class], dependencies = [AppComponent::class])
@PerFragmentScope
interface ListFragmentComponent {

    fun injectListFragment(listFragment: ListFragment)
}