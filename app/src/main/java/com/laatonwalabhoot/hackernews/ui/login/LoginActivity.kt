package com.laatonwalabhoot.hackernews.ui.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.laatonwalabhoot.hackernews.R
import com.laatonwalabhoot.hackernews.ui.list.ListActivity

class LoginActivity : AppCompatActivity() {

    /************************************
     * OVERRIDDEN METHODS
     ************************************/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LoginFragment.newInstance())
                    .commitNow()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.findFragmentById(R.id.container).onActivityResult(requestCode, resultCode, data)
    }
}
