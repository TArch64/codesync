package ua.tarch64.room.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import ua.tarch64.plugin.PluginConfig
import ua.tarch64.room.RoomModule
import ua.tarch64.shared.ModuleInjector

class LeaveAction: AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        ModuleInjector.inject<RoomModule>().leave()
    }

    override fun update(event: AnActionEvent) {
        val isConnected = ModuleInjector.inject<PluginConfig>().isConnectedToRoom()
        event.presentation.isEnabledAndVisible = isConnected
    }
}