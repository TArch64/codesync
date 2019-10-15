package ua.tarch64.shared.ui

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType

enum class Type(val nativeType: NotificationType) {
    INFO(NotificationType.INFORMATION),
    ERROR(NotificationType.ERROR)
}

class Toasts {
    private fun notify(message: String, type: Type) {
        val notification = Notification("ua.tarch64.codesync", "CodeSync", message, type.nativeType)
        com.intellij.notification.Notifications.Bus.notify(notification)
    }

    fun notifyInfo(message: String) {
        this.notify(message, Type.INFO)
    }

    fun notifyError(message: String) {
        this.notify(message, Type.ERROR)
    }
}