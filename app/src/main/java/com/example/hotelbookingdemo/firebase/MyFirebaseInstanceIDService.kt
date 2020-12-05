package com.example.hotelbookingdemo.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.hotelbookingdemo.MainActivity
import com.example.hotelbookingdemo.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson


class MyFirebaseMessagingService : FirebaseMessagingService() {
    //    internal var utils = Utilities()
    internal var tripId = ""
    internal var providerId = ""//17 may avinash
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        if (remoteMessage.data != null) {
            remoteMessage.data["data"]
            createNotification(remoteMessage.data)
        } else {

        }
    }



    fun createNotification(data: Map<String, String>) {


        val custom = data["custom"]
        try {
            //  JSONObject jsonObject = new JSONObject(custom);
            val fcmChartResponse = Gson().fromJson(custom, FCMChartResponse::class.java)
            if (fcmChartResponse != null) {
                providerId = fcmChartResponse.providerId.toString() + ""
                if (fcmChartResponse.tripId == null) {
                    tripId = ""
                } else {
                    tripId = fcmChartResponse.tripId
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val mNotificationManager: NotificationManager?
        val mBuilder: NotificationCompat.Builder
        val NOTIFICATION_CHANNEL_ID = "10001"
        val uniqueInt = System.currentTimeMillis().toInt()

        /**Creates an explicit intent for an Activity in your app */
        //17 may avinash
        val resultIntent: Intent

        val channelId = getString(R.string.channel_id)
        val channelName = getString(R.string.channel_name)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupNotificationChannels(channelId, channelName, notificationManager)
        }


        if (!providerId.equals("", ignoreCase = true) && !tripId.equals("", ignoreCase = true)) {
            resultIntent = Intent(this, MainActivity::class.java)
            //ChatMessagingActivity.class);
            resultIntent.putExtra("providerId", providerId)
            resultIntent.putExtra("tripId", tripId)

        } else {
            resultIntent = Intent(this, MainActivity::class.java)
            resultIntent.putExtra("push", true)
        }

        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val resultPendingIntent = PendingIntent.getActivity(
            this,
            uniqueInt/* Request code */, resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        mBuilder = NotificationCompat.Builder(this)
        mBuilder.setSmallIcon(getNotificationIcon(mBuilder), 1)
        mBuilder.setContentTitle(data["title"])
            .setContentText(data["message"])
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(resultPendingIntent)

        mNotificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel =
                NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "NOTIFICATION_CHANNEL_NAME",
                    importance
                )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern =
                longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            assert(mNotificationManager != null)
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID)
            mNotificationManager.createNotificationChannel(notificationChannel)
        }
        assert(mNotificationManager != null)
        mNotificationManager.notify(uniqueInt /* Request Code */, mBuilder.build())
    }



    private fun getNotificationIcon(notificationBuilder: NotificationCompat.Builder): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.color =
                ContextCompat.getColor(applicationContext, R.color.purple_200)
            return R.drawable.ic_menu_notification
        } else {
            return R.drawable.ic_menu_notification
        }
    }

    companion object {


        private val TAG = "MyFirebaseMsgService"
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun setupNotificationChannels(
        channelId: String,
        channelName: String,
        notificationManager: NotificationManager
    ) {

        val channel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        channel.enableLights(true)
        channel.lightColor = Color.GREEN
        channel.enableVibration(true)
        notificationManager.createNotificationChannel(channel)
    }
}
