package com.laatonwalabhoot.hackernews.ui.list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import com.laatonwalabhoot.hackernews.R
import com.laatonwalabhoot.hackernews.data.models.Article

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ListFragment.newInstance())
                    .commitNow()
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}