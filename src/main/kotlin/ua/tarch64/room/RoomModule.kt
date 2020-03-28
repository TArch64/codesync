package ua.tarch64.room

import ua.tarch64.plugin.PluginConfig
import ua.tarch64.plugin.PluginModule
import ua.tarch64.shared.ModuleInjector
import ua.tarch64.shared.Toasts
import ua.tarch64.shared.dispatcher.Event
import ua.tarch64.shared.dispatcher.events.rooms.*

class RoomModule: PluginModule() {
    private val toasts: Toasts = ModuleInjector.inject()
    private val config: PluginConfig = ModuleInjector.inject()

    override fun up() {
        this.dispatcher.listen(RoomCreatedEvent.NAME).subscribe(this::onRoomCreated)
        this.dispatcher.listen(RoomJoinedEvent.NAME).subscribe(this::onJoined)
        this.dispatcher.listen(RoomCollaboratorJoinedEvent.NAME).subscribe(this::onCollaboratorJoined)
    }

    fun createRoom() {
        val event = RoomCreateEvent(RoomCreateEventPayload(
            username = this.config.username
        ))
        this.dispatcher.trigger(event)
    }

    private fun onRoomCreated(event: Event) {
        val roomId = (event.payload as RoomCreatedEventPayload).roomId
        this.config.roomId = roomId
        this.toasts.notifyInfo("Created room:\n $roomId")
    }

    fun join(roomId: String) {
        val event = RoomJoinEvent(RoomJoinEventPayload(
            username = this.config.username,
            roomId = roomId
        ))
        this.dispatcher.trigger(event)
    }

    private fun onJoined(event: Event) {
        val roomId = (event.payload as RoomJoinedEventPayload).roomId
        this.config.roomId = roomId
        this.toasts.notifyInfo("Joined to room")
    }

    private fun onCollaboratorJoined(event: Event) {
        val username = (event.payload as RoomCollaboratorJoinedEventPayload).username
        this.toasts.notifyInfo("$username has joined to room")
    }
}