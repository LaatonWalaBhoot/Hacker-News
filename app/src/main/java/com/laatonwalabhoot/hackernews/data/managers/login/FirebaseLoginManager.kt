package com.laatonwalabhoot.hackernews.data.managers.login

import android.app.Activity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.laatonwalabhoot.hackernews.R
import com.laatonwalabhoot.hackernews.common.Constants
import java.util.*

class FirebaseLoginManager {

    companion object {
        private var firebaseLoginManager: FirebaseLoginManager = FirebaseLoginManager()

        @Synchronized
        fun getInstance() : FirebaseLoginManager {
            return firebaseLoginManager
        }
    }

    /************************************
     * PUBLIC METHODS
     ************************************/
    fun initIntent(activity: Activity) {
        val providers = Arrays.asList(
                AuthUI.IdpConfig.PhoneBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build(),
                AuthUI.IdpConfig.FacebookBuilder().build())

        activity.startActivityForResult( AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.AppTheme)
                .build(),
                Constants.RC_SIGN_IN)
    }

    fun deleteUser(activity: Activity) {
        AuthUI.getInstance()
                .delete(activity)
    }

    fun signOut(activity: Activity) {
        AuthUI.getInstance()
                .signOut(activity)
    }
}