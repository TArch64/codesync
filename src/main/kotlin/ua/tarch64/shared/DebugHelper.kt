package ua.tarch64.shared

import ua.tarch64.shared.moduleInjection.InjectionModule

object DebugHelper {
    private val toasts = InjectionModule().injectModule(Toasts::class.java)

    fun consoleLog(data: Any) {
        toasts.notifyInfo(data.toString())
    }
}