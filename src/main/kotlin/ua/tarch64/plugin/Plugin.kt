package ua.tarch64.plugin

import ua.tarch64.changeEmitter.ChangesEmitter
import ua.tarch64.documentListener.DocumentsListener
import ua.tarch64.DocumentUpdater
import ua.tarch64.shared.dispatcher.Dispatcher
import ua.tarch64.shared.dispatcher.events.PluginDownEvent
import ua.tarch64.shared.dispatcher.events.PluginUpEvent
import ua.tarch64.shared.Gateway
import ua.tarch64.shared.moduleInjection.InjectionModule

class Plugin: InjectionModule() {
    private val dispatcher = this.injectModule(Dispatcher::class.java)
    private val pluginState = this.injectModule(PluginState::class.java)

    init {
        this.injectModule(Gateway::class.java)
        this.injectModule(DocumentsListener::class.java)
        this.injectModule(ChangesEmitter::class.java)
        this.injectModule(DocumentUpdater::class.java)
    }

    fun configure(updateConfig: PluginConfig.() -> Unit) {
        this.pluginState.config = PluginConfig().apply(updateConfig)
    }

    fun up() {
        this.dispatcher.trigger(PluginUpEvent())
        this.pluginState.isConnectedToRoom = true
    }

    fun down() {
        this.dispatcher.trigger(PluginDownEvent())
        this.pluginState.isConnectedToRoom = false
    }
}