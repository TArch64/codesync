package ua.tarch64.room.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import ua.tarch64.plugin.Plugin
import ua.tarch64.shared.moduleInjection.IInjectionModule
import ua.tarch64.shared.moduleInjection.InjectionModule

class ConnectAction: AnAction(), IInjectionModule by InjectionModule() {
    private val plugin = this.injectModule(Plugin::class.java)

    override fun actionPerformed(event: AnActionEvent) = this.plugin.up()
}