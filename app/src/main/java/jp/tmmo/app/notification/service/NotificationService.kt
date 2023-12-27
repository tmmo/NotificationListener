package jp.tmmo.app.notification.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class NotificationService : NotificationListenerService() {
    companion object {
        private const val TAG = "NotificationService"
        private val apps = listOf(
            "jp.co.sonymusic.communication.sakurazaka",
            "jp.co.sonymusic.communication.nogizaka"
        )
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.d(TAG, "onListenerConnected")
        val notifications = activeNotifications
        Log.d(TAG, "notifications=$notifications")
        notifications.forEach {
            notify(it)
        }
    }

    private fun notify(statusBarNotification: StatusBarNotification) {
        if (!apps.contains(statusBarNotification.packageName)) {
            return
        }
        val notification = statusBarNotification.notification
        Log.d(TAG, "notification=$notification")
        Log.d(TAG, "ticketText=${notification.tickerText}")
        val extras = notification.extras
        extras.keySet().forEach {
            Log.d(TAG, "key=$it value=${extras.get(it)}")
        }
        notification.actions?.forEach {
            Log.d(TAG, "title=${it.title} action=$it")
        }
        val intent = notification.contentIntent
        Log.d(TAG, "contentIntent=$intent")
        intent.send()
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        sbn ?: return
        notify(sbn)
    }

    override fun onListenerDisconnected() {
        super.onListenerDisconnected()
        Log.d(TAG, "onListenerDisconnected")
    }
}