package ua.tarch64.plugin

import ua.tarch64.changeEmitter.ChangesEmitter
import ua.tarch64.documentListener.DocumentsListener
import ua.tarch64.DocumentUpdater
import ua.tarch64.room.RoomModule
import ua.tarch64.shared.dispatcher.Dispatcher
import ua.tarch64.shared.dispatcher.events.PluginDownEvent
import ua.tarch64.shared.dispatcher.events.PluginUpEvent
import ua.tarch64.shared.Gateway
import ua.tarch64.shared.ModuleInjector

class Plugin {
    private val dispatcher: Dispatcher = ModuleInjector.inject()
    private val pluginState: PluginState = ModuleInjector.inject()
    private val modules = listOf(
        Gateway::class,
        RoomModule::class,
        DocumentsListener::class,
        ChangesEmitter::class,
        DocumentUpdater::class
    )

    init {
        this.modules.forEach { ModuleInjector.inject(it) }
    }

    fun configure(updateConfig: PluginConfig.() -> Unit) {
        this.pluginState.config = this.pluginState.config.apply(updateConfig)
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