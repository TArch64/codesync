package ua.tarch64.room.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import ua.tarch64.plugin.Plugin
import ua.tarch64.plugin.PluginState
import ua.tarch64.room.ui.ConnectRoomDialog
import ua.tarch64.shared.ModuleInjector

class ConnectAction: AnAction() {
    override fun actionPerformed(event: AnActionEvent) = ConnectRoomDialog().onSubmit {
        val plugin: Plugin = ModuleInjector.inject()
        plugin.configure {
            gatewayServiceUrl = this@onSubmit.serviceUrl
        }
        plugin.up()
    }

    override fun update(event: AnActionEvent) {
        val isConnected = ModuleInjector.inject<PluginState>().isConnectedToRoom
        event.presentation.isEnabledAndVisible = !isConnected
    }
}