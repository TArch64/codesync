package ua.tarch64.plugin

import ua.tarch64.changeEmitter.ChangesEmitter
import ua.tarch64.documentListener.DocumentsListener
import ua.tarch64.DocumentUpdater
import ua.tarch64.room.RoomModule
import ua.tarch64.dispatcher.Dispatcher
import ua.tarch64.dispatcher.Event
import ua.tarch64.dispatcher.events.plugin.PluginDownEvent
import ua.tarch64.dispatcher.events.plugin.PluginErrorEvent
import ua.tarch64.dispatcher.events.plugin.PluginErrorEventPayload
import ua.tarch64.dispatcher.events.plugin.PluginUpEvent
import ua.tarch64.shared.Gateway
import ua.tarch64.shared.ModuleInjector
import ua.tarch64.shared.Toasts

class Plugin {
    private var isActive: Boolean = false
    private val dispatcher: Dispatcher = ModuleInjector.inject()
    private val toasts: Toasts = ModuleInjector.inject()
    private val modules = listOf(
        PluginState::class,
        Gateway::class,
        RoomModule::class,
        DocumentsListener::class,
        ChangesEmitter::class,
        DocumentUpdater::class
    )

    init {
        this.modules.forEach { ModuleInjector.inject(it) }
    }

    fun up() {
        if (!this.isActive) {
            this.isActive = true
            this.dispatcher.trigger(PluginUpEvent())
            this.dispatcher.listen(PluginErrorEvent.NAME).subscribe(this::onError)
        }
    }

    fun down() {
        this.isActive = false
        this.dispatcher.trigger(PluginDownEvent())
    }

    private fun onError(event: Event) {
        val description = (event.payload as PluginErrorEventPayload).description
        this.toasts.notifyError(description)
    }
}