package ua.tarch64.room

import ua.tarch64.plugin.PluginModule
import ua.tarch64.shared.ModuleInjector
import ua.tarch64.shared.Toasts
import ua.tarch64.shared.dispatcher.Event
import ua.tarch64.shared.dispatcher.events.CreateRoomEvent
import ua.tarch64.shared.dispatcher.events.RoomCreatedEvent

class RoomModule: PluginModule() {
    private val toasts: Toasts = ModuleInjector.inject()

    override fun down() = Unit
    override fun up() {
        this.dispatcher.listen(RoomCreatedEvent.NAME).subscribe(this::onRoomCreated)
    }

    fun createRoom() {
        this.dispatcher.trigger(CreateRoomEvent())
    }

    private fun onRoomCreated(event: Event) {
        val roomId = event.payload as String
        this.toasts.notifyInfo("Created room:\n $roomId")
    }
}