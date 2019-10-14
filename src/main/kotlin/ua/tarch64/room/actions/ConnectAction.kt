package ua.tarch64.room.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import ua.tarch64.plugin.Plugin

class ConnectAction: AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        Plugin.instance.up()
    }
}