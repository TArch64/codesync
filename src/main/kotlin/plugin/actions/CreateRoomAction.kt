package plugin.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import plugin.Plugin

class CreateRoomAction: AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        Plugin.instance.up()
    }
}