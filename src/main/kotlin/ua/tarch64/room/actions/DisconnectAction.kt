package ua.tarch64.room.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import ua.tarch64.plugin.Plugin
import ua.tarch64.plugin.PluginState
import ua.tarch64.shared.ModuleInjector

class DisconnectAction: AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        ModuleInjector.inject<Plugin>().down()
    }

    override fun update(event: AnActionEvent) {
        val isConnected = ModuleInjector.inject<PluginState>().isConnectedToRoom
        event.presentation.isEnabledAndVisible = isConnected
    }
}