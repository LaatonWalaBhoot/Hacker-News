package com.laatonwalabhoot.hackernews

import android.app.Application
import io.realm.Realm

class HackerNews: Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}