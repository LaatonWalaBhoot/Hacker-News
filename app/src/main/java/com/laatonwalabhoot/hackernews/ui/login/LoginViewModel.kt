package com.laatonwalabhoot.hackernews.ui.login

import android.app.Activity.RESULT_OK
import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.util.Log
import com.firebase.ui.auth.IdpResponse
import com.laatonwalabhoot.hackernews.common.Constants
import com.laatonwalabhoot.hackernews.common.Constants.Companion.RC_SIGN_IN

class LoginViewModel : ViewModel() {

    private lateinit var loginNavigator: LoginNavigator

    /************************************
     * PUBLIC METHODS
     ************************************/
    fun setLoginNavigator(loginNavigator: LoginNavigator) {
        this.loginNavigator = loginNavigator
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == RC_SIGN_IN) {
            val response: IdpResponse = IdpResponse.fromResultIntent(data)!!
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                loginNavigator.onLoginSuccess()
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                loginNavigator.onLoginError(response.error.toString())
            }
        }
    }
}
