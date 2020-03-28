package ua.tarch64.shared

import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import ua.tarch64.plugin.PluginModule
import ua.tarch64.plugin.PluginState
import ua.tarch64.shared.dispatcher.Event as DispatcherEvent
import ua.tarch64.shared.dispatcher.events.SendDocumentChangesEvent
import ua.tarch64.shared.dispatcher.events.UpdateDocumentEvent
import ua.tarch64.shared.dispatcher.events.rooms.*
import ua.tarch64.shared.models.DocumentChanges

class Gateway: PluginModule() {
    private val pluginState: PluginState = ModuleInjector.inject()
    private lateinit var socket: Socket

    override fun up() {
        this.socket = IO.socket(this.pluginState.config.gatewayServiceUrl).connect()
        this.socket.on(UpdateDocumentEvent.NAME, this::onReceivedExternalChanges)
        this.dispatcher.listen(SendDocumentChangesEvent.NAME).subscribe(this::sendDocumentChanges)

        // Rooms events
        this.dispatcher.listen(RoomCreateEvent.NAME).subscribe(this::onCreateRoom)
        this.socket.on(RoomCreatedEvent.NAME, this::onCreatedRoom)

        this.dispatcher.listen(RoomJoinEvent.NAME).subscribe(this::onJoinRoom)
        this.socket.on(RoomJoinedEvent.NAME, this::onJoinedRoom)
        this.socket.on(RoomCollaboratorJoinedEvent.NAME, this::onJoinedCollaboratorRoom)
    }

    private fun sendDocumentChanges(event: DispatcherEvent) {
        val json = (event.payload as DocumentChanges).toJSON()
        this.socket.emit(event.name, json)
    }

    private fun onReceivedExternalChanges(vararg args: Any) {
        val documentChanges = DocumentChanges.fromJSON(args.first() as JSONObject)
        val event = UpdateDocumentEvent(documentChanges)
        this.dispatcher.trigger(event)
    }

    override fun down() {
        this.socket.off()
        this.socket = this.socket.disconnect()
    }

    // Rooms handlers

    private fun onCreateRoom(event: DispatcherEvent) {
        val json = (event.payload as RoomCreateEventPayload).toJSON()
        this.socket.emit(event.name, json)
    }

    private fun onCreatedRoom(vararg args: Any) {
        val event = RoomCreatedEvent.fromJSON(args.first() as JSONObject)
        this.dispatcher.trigger(event)
    }

    private fun onJoinRoom(event: DispatcherEvent) {
        val json = (event.payload as RoomJoinEventPayload).toJSON()
        this.socket.emit(event.name, json)
    }

    private fun onJoinedRoom(vararg args: Any) {
        val event = RoomJoinedEvent.fromJSON(args.first() as JSONObject)
        this.dispatcher.trigger(event)
    }

    private fun onJoinedCollaboratorRoom(vararg args: Any) {
        val event = RoomCollaboratorJoinedEvent.fromJSON(args.first() as JSONObject)
        this.dispatcher.trigger(event)
    }
}