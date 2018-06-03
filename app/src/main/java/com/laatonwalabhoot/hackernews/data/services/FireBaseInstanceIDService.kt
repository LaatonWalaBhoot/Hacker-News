package com.laatonwalabhoot.hackernews.data.services

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class FireBaseInstanceIDService: FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        super.onTokenRefresh()

        //Getting registration token
        val refreshedToken: String = FirebaseInstanceId.getInstance().token!!
        //Displaying token on logcat
        Log.d("", "Refreshed token: $refreshedToken")
    }
}