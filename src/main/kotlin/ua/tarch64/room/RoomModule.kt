package ua.tarch64.room

import ua.tarch64.plugin.Plugin
import ua.tarch64.plugin.PluginConfig
import ua.tarch64.plugin.PluginModule
import ua.tarch64.shared.ModuleInjector
import ua.tarch64.shared.Toasts
import ua.tarch64.dispatcher.Event
import ua.tarch64.dispatcher.events.rooms.*

class RoomModule: PluginModule() {
    private val toasts: Toasts = ModuleInjector.inject()
    private val config: PluginConfig = ModuleInjector.inject()

    override fun up() {
        // Create room events
        this.dispatcher.listen(RoomCreatedEvent.NAME).subscribe(this::onRoomCreated)
        // Join room events
        this.dispatcher.listen(RoomJoinedEvent.NAME).subscribe(this::onJoined)
        this.dispatcher.listen(RoomCollaboratorJoinedEvent.NAME).subscribe(this::onCollaboratorJoined)
        // Leave room events
        this.dispatcher.listen(RoomLeftEvent.NAME).subscribe(this::onLeft)
        this.dispatcher.listen(RoomCollaboratorLeftEvent.NAME).subscribe(this::onCollaboratorLeft)
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
        this.toasts.notifyInfo("Successfully joined to room")
    }

    private fun onCollaboratorJoined(event: Event) {
        val username = (event.payload as RoomCollaboratorJoinedEventPayload).username
        this.toasts.notifyInfo("$username has joined to room")
    }

    fun leave() {
        val event = RoomLeaveEvent()
        this.dispatcher.trigger(event)
    }

    private fun onLeft(event: Event) {
        this.toasts.notifyInfo("Successfully left from room")
        this.config.resetRoomData()
        ModuleInjector.inject<Plugin>().down()
    }

    private fun onCollaboratorLeft(event: Event) {
        val username = (event.payload as RoomCollaboratorLeftEventPayload).username
        this.toasts.notifyInfo("$username has left from room")
    }
}