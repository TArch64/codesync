package ua.tarch64.shared

import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import ua.tarch64.plugin.PluginModule
import ua.tarch64.plugin.PluginState
import ua.tarch64.dispatcher.Event as DispatcherEvent
import ua.tarch64.dispatcher.events.plugin.PluginErrorEvent
import ua.tarch64.dispatcher.events.rooms.*
import ua.tarch64.dispatcher.events.editor.*

class Gateway: PluginModule() {
    private val pluginState: PluginState = ModuleInjector.inject()
    private lateinit var socket: Socket

    override fun up() {
        this.socket = IO.socket(this.pluginState.config.gatewayServiceUrl).connect()

        // General events
        this.socket.on(PluginErrorEvent.NAME, this::onError)

        // Rooms create events
        this.dispatcher.listen(RoomCreateEvent.NAME).subscribe(this::onRoomCreate)
        this.socket.on(RoomCreatedEvent.NAME, this::onRoomCreated)
        // Rooms join events
        this.dispatcher.listen(RoomJoinEvent.NAME).subscribe(this::onRoomJoin)
        this.socket.on(RoomJoinedEvent.NAME, this::onRoomJoined)
        this.socket.on(RoomCollaboratorJoinedEvent.NAME, this::onRoomCollaboratorJoined)
        // Rooms leave events
        this.dispatcher.listen(RoomLeaveEvent.NAME).subscribe(this::onRoomLeave)
        this.socket.on(RoomLeftEvent.NAME, this::onRoomLeft)
        this.socket.on(RoomCollaboratorLeftEvent.NAME, this::onRoomCollaboratorLeft)

        // Editor Text events
        this.dispatcher.listen(EditorOutputTextChangesEvent.NAME).subscribe(this::onEditorOutputTextChanges)
        this.socket.on(EditorInputTextChangesEvent.NAME, this::onEditorInputTextChanges)
    }

    override fun down() {
        this.socket.off()
        this.socket = this.socket.disconnect()
    }

    private fun onError(vararg args: Any) {
        val event = PluginErrorEvent.fromJSON(args.first() as JSONObject)
        this.dispatcher.trigger(event)
    }

    // Rooms create handlers

    private fun onRoomCreate(event: DispatcherEvent) {
        val json = (event.payload as RoomCreateEventPayload).toJSON()
        this.socket.emit(event.name, json)
    }

    private fun onRoomCreated(vararg args: Any) {
        val event = RoomCreatedEvent.fromJSON(args.first() as JSONObject)
        this.dispatcher.trigger(event)
    }

    // Rooms join handlers

    private fun onRoomJoin(event: DispatcherEvent) {
        val json = (event.payload as RoomJoinEventPayload).toJSON()
        this.socket.emit(event.name, json)
    }

    private fun onRoomJoined(vararg args: Any) {
        val event = RoomJoinedEvent.fromJSON(args.first() as JSONObject)
        this.dispatcher.trigger(event)
    }

    private fun onRoomCollaboratorJoined(vararg args: Any) {
        val event = RoomCollaboratorJoinedEvent.fromJSON(args.first() as JSONObject)
        this.dispatcher.trigger(event)
    }

    // Rooms leave handlers

    private fun onRoomLeave(event: DispatcherEvent) {
        this.socket.emit(event.name)
    }

    private fun onRoomLeft(vararg args: Any) {
        val event = RoomLeftEvent()
        this.dispatcher.trigger(event)
    }

    private fun onRoomCollaboratorLeft(vararg args: Any) {
        val event = RoomCollaboratorLeftEvent.fromJSON(args.first() as JSONObject)
        this.dispatcher.trigger(event)
    }

    // Editor Text events

    private fun onEditorOutputTextChanges(event: DispatcherEvent) {
        val json = (event.payload as EditorOutputTextChangesEventPayload).changes.toJSON()
        this.socket.emit(event.name, json)
    }

    private fun onEditorInputTextChanges(vararg args: Any) {
        val event = EditorInputTextChangesEvent.fromJSON(args.first() as JSONObject)
        this.dispatcher.trigger(event)
    }
}