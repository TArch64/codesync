package ua.tarch64.room.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import ua.tarch64.plugin.Plugin
import ua.tarch64.plugin.PluginState
import ua.tarch64.room.ui.ConnectRoomDialog
import ua.tarch64.shared.moduleInjection.IInjectionModule
import ua.tarch64.shared.moduleInjection.InjectionModule

class ConnectAction: AnAction(), IInjectionModule by InjectionModule() {
    override fun actionPerformed(event: AnActionEvent) = ConnectRoomDialog().onSubmit {
        val plugin = this@ConnectAction.injectModule(Plugin::class.java)
        plugin.configure {
            gatewayServiceUrl = this@onSubmit.serviceUrl
        }
        plugin.up()
    }

    override fun update(event: AnActionEvent) {
        val isConnected = this.injectModule(PluginState::class.java).isConnectedToRoom
        event.presentation.isEnabledAndVisible = !isConnected
    }
}