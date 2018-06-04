package com.laatonwalabhoot.hackernews.di.modules

import com.laatonwalabhoot.hackernews.di.scopes.PerFragmentScope
import com.laatonwalabhoot.hackernews.ui.detail.comments.CommentsAdapter
import dagger.Module
import dagger.Provides

@Module
class CommentsAdapterModule {

    @Provides
    @PerFragmentScope
    fun commentsAdapter(): CommentsAdapter {
        return CommentsAdapter()
    }
}