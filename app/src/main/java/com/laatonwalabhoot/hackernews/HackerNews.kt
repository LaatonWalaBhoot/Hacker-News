package com.laatonwalabhoot.hackernews

import android.app.Activity
import android.app.Application
import com.laatonwalabhoot.hackernews.di.components.AppComponent
import com.laatonwalabhoot.hackernews.di.components.DaggerAppComponent
import io.realm.Realm

class HackerNews : Application() {

    private lateinit var component: AppComponent

    companion object {
        fun newInstance() = HackerNews()
    }

    /************************************
     * OVERRIDDEN METHODS
     ************************************/
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        component = DaggerAppComponent.builder()
                .build()
    }

    /************************************
     * PUBLIC METHODS
     ************************************/

    fun getApp(activity: Activity): HackerNews {
        return activity.application as HackerNews
    }

    fun getAppComponent(): AppComponent {
        return component
    }
}