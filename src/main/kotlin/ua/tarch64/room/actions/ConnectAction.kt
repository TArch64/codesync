package ua.tarch64.room.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import ua.tarch64.plugin.Plugin
import ua.tarch64.plugin.PluginConfig
import ua.tarch64.room.ui.ConnectRoomDialog
import ua.tarch64.shared.moduleInjection.IInjectionModule
import ua.tarch64.shared.moduleInjection.InjectionModule

class ConnectAction: AnAction(), IInjectionModule by InjectionModule() {
    override fun actionPerformed(event: AnActionEvent) {
        val dialog = ConnectRoomDialog()

        if ( !dialog.showAndGet() ) { return }

        val plugin = this.injectModule(Plugin::class.java)
        plugin.setConfig(PluginConfig(
            gatewayServiceUrl = dialog.serviceUrlGetter()
        ))
        plugin.up()
    }
}