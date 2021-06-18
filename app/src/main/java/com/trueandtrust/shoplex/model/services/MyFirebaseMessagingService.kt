package com.trueandtrust.shoplex.model.services


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.trueandtrust.shoplex.R
import com.trueandtrust.shoplex.model.extra.StoreInfo
import com.trueandtrust.shoplex.view.activities.HomeActivity
import com.trueandtrust.shoplex.view.activities.auth.AuthActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val NOTIFICATION_CHANNEL_ID = "net.larntech.notification"
    val NOTIFICATION_ID = 100

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data.isNotEmpty()) {
            val title = remoteMessage.data[this.getString(R.string.title)]
            val body = remoteMessage.data[this.getString(R.string.body)]
            showNotification(applicationContext, title, body)
        } else {
            val title = remoteMessage.notification!!.title
            val body = remoteMessage.notification!!.body
            showNotification(applicationContext, title, body)
        }
    }

    private fun showNotification(
        context: Context,
        title: String?,
        message: String?
    ) {
        val ii: Intent
        var pi: PendingIntent? = null
        if(title?.contains("Product Accepted", true) == true){
            ii = Intent(context, HomeActivity::class.java)
            ii.data = Uri.parse(context.getString(R.string.custom) + System.currentTimeMillis())
            ii.action = context.getString(R.string.actionstring) + System.currentTimeMillis()
            ii.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            pi =PendingIntent.getActivity(context, 0, ii, PendingIntent.FLAG_UPDATE_CURRENT)
            StoreInfo.readStoreInfo(context)
        }else if(title?.contains("Account Accepted", true) == true){
            ii = Intent(context, AuthActivity::class.java)
            ii.data = Uri.parse(context.getString(R.string.custom) + System.currentTimeMillis())
            ii.action = context.getString(R.string.actionstring) + System.currentTimeMillis()
            ii.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            pi =PendingIntent.getActivity(context, 0, ii, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification: Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Log.e("Notification", "Created in up to orio OS device");
            notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                //.setOngoing(true)
                .setSmallIcon(getNotificationIcon())
                .setContentText(message)
                .setAutoCancel(true)
                //.setContentIntent(pi)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setWhen(System.currentTimeMillis())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle(title).apply {
                    if(pi != null)
                        this.setContentIntent(pi)
                }.build()
            val notificationManager = context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                title,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.notify(NOTIFICATION_ID, notification)
        } else {
            notification = NotificationCompat.Builder(context)
                .setSmallIcon(getNotificationIcon())
                .setAutoCancel(true)
                .setContentText(message)
                //.setContentIntent(pi)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle(title).apply {
                    if(pi != null)
                        this.setContentIntent(pi)
                }.build()
            val notificationManager = context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

    private fun getNotificationIcon(): Int {
        val useWhiteIcon =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        return if (useWhiteIcon) R.mipmap.ic_launcher else R.mipmap.ic_launcher
    }

}