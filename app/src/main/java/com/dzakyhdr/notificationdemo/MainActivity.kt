package com.dzakyhdr.notificationdemo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.dzakyhdr.notificationdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val channelID = "Growdev"
    private val KEY_REPLY = "key_reply"

    private var notificationManager: NotificationManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationChannelCreate(channelID, "HaidarChannel", "Ini Notifikasi")
        binding.button.setOnClickListener {
            displayNotification()
        }
    }

    private fun displayNotification() {
        val notificationId = 10
        val tapResultIntent = Intent(this, SecondActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                tapResultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        //action button 1
        val intent2 = Intent(this, DetailActivity::class.java)
        val pendingIntent2 = PendingIntent.getActivity(
                this,
                0,
                intent2,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        val action2 = NotificationCompat.Action.Builder(
                0,
                "Details",
                pendingIntent2).build()

        // reply action
        val remoteInput = RemoteInput.Builder(KEY_REPLY).run {
            setLabel("Insert Your Name")
            build()
        }

        val replyAction = NotificationCompat.Action.Builder(
                0,
                "Reply",
                pendingIntent
        ).addRemoteInput(remoteInput).build()

        //action button 2
        val intent3 = Intent(this, SettingActivity::class.java)
        val pendingIntent3 = PendingIntent.getActivity(
                this,
                0,
                intent3,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        val action3 = NotificationCompat.Action.Builder(
                0,
                "Settings",
                pendingIntent3).build()

        val notification = NotificationCompat.Builder(this@MainActivity, channelID)
                .setContentTitle("Demo Aplikasi")
                .setContentText("Ini Adalah Sebuah Demo Aplikasi")
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setAutoCancel(true)
                .addAction(replyAction)
                .addAction(action2)
                .addAction(action3)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()
        notificationManager?.notify(notificationId, notification)
    }

    private fun notificationChannelCreate(id: String, name: String, channelDescription: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance).apply {
                description = channelDescription
            }

            notificationManager?.createNotificationChannel(channel)
        }
    }
}