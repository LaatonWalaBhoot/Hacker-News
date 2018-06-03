package com.laatonwalabhoot.hackernews.ui.login

interface LoginNavigator {

    fun onLoginSuccess()

    fun onLoginError(error: String)

    fun onLoginCancel()
}