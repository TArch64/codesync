package ua.tarch64.shared

object DebugHelper {
    private val toasts: Toasts = ModuleInjector.inject()

    fun consoleLog(data: Any) {
        toasts.notifyInfo(data.toString())
    }
}