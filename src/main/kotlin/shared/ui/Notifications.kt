package shared.ui

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType

class Notifications {
    fun notify(message: String, type: Type) {
        val notification = Notification("ua.tarch64.codesync", "CodeSync", message, type.nativeType)
        com.intellij.notification.Notifications.Bus.notify(notification)
    }

    companion object {
        private lateinit var instance: Notifications

        fun setup() {
           this.instance = Notifications()
        }

        fun notifyInfo(message: String) {
            instance.notify(message, Type.INFO)
        }

        fun notifyError(message: String) {
            instance.notify(message, Type.ERROR)
        }

        enum class Type(val nativeType: NotificationType) {
            INFO(NotificationType.INFORMATION),
            ERROR(NotificationType.ERROR)
        }
    }
}