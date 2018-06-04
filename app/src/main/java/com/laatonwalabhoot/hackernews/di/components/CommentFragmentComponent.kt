package com.laatonwalabhoot.hackernews.di.components

import com.laatonwalabhoot.hackernews.di.modules.CommentsAdapterModule
import com.laatonwalabhoot.hackernews.di.scopes.PerFragmentScope
import com.laatonwalabhoot.hackernews.ui.detail.comments.CommentFragment
import dagger.Component
import dagger.Module

@PerFragmentScope
@Component(modules = [CommentsAdapterModule::class], dependencies = [AppComponent::class])
interface CommentFragmentComponent {

    fun injectCommentFragment(commentFragment: CommentFragment)
}