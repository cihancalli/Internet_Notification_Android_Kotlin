package com.zerdasoftware.internetnotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotificationService :FirebaseMessagingService() {
    var count = 1

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val title = message.notification?.title
        val body = message.notification?.body

        createNotification(title,body)
    }

    private fun createNotification(title:String?, body:String?) {
        val builder : NotificationCompat.Builder

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(this,MainActivity::class.java)

        //PendingIntent.FLAG_UPDATE_CURRENT

        val goToIntent = PendingIntent.getActivity(applicationContext,1,intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {

            val channelID = "channelID$count"
            val channelName = "channelName$count"
            val channelDescription = "channelDescription$count"
            val channelPriority = NotificationManager.IMPORTANCE_HIGH

            var channel: NotificationChannel? = notificationManager.getNotificationChannel(channelID)

            if (channel == null) {
                channel = NotificationChannel(channelID,channelName,channelPriority)
                channel.description = channelDescription
                notificationManager.createNotificationChannel(channel)
            }

            builder = NotificationCompat.Builder(this,channelID)
            builder.setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(goToIntent)
                .setAutoCancel(true)

        }

        else {
            builder = NotificationCompat.Builder(this)
            builder.setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(goToIntent)
                .setAutoCancel(true)
                .priority = Notification.PRIORITY_HIGH
        }

        notificationManager.notify(count,builder.build())
        count ++
    }
}