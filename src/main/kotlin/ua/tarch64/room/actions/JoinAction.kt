package ua.tarch64.room.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import ua.tarch64.plugin.Plugin
import ua.tarch64.plugin.PluginConfig
import ua.tarch64.room.RoomModule
import ua.tarch64.room.ui.JoinRoomDialog
import ua.tarch64.shared.ModuleInjector

class JoinAction: AnAction() {
    override fun actionPerformed(event: AnActionEvent) = JoinRoomDialog().onSubmit {
        ModuleInjector.inject<Plugin>().up()
        ModuleInjector.inject<RoomModule>().join(this.roomId)
    }

    override fun update(event: AnActionEvent) {
        val isConnected = ModuleInjector.inject<PluginConfig>().isConnectedToRoom()
        event.presentation.isEnabledAndVisible = !isConnected
    }
}