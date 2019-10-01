package ua.tarch64.shared.gateway

import ua.tarch64.shared.events.Events
import ua.tarch64.shared.ui.Toasts

class GatewayService: Events() {
    private var process: Process? = null

    fun up() {
//        this.process = Runtime.getRuntime().exec("node")
//        this.process?.inputStream?
        val files = Runtime.getRuntime().exec("dir").inputStream.bufferedReader().readLine()
        Toasts.notifyInfo(files)
    }

    fun down() {

    }
}