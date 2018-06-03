package com.laatonwalabhoot.hackernews.data.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.laatonwalabhoot.hackernews.R

class FirebaseMessagingService: FirebaseMessagingService() {

    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private var count = 0
    private val pendingIntent: PendingIntent? = null

    override fun onMessageReceived(p0: RemoteMessage) {
        //Calling method to generate notification
        sendNotification(p0.notification!!.title!!, p0.notification!!.body!!, p0.data)
    }

    //This method is only generating push notification
    private fun sendNotification(messageTitle: String, messageBody: String, row: Map<String, String>) {
        notificationBuilder = NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

         notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(count, notificationBuilder.build())
        count++
    }
}