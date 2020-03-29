package ua.tarch64.dispatcher.events.rooms

import org.json.JSONObject
import ua.tarch64.dispatcher.Event

class RoomCreateEvent(payload: RoomCreateEventPayload): Event(NAME, payload) {
    companion object { const val NAME = "rooms::create" }
}

data class RoomCreateEventPayload(val username: String) {
    fun toJSON(): JSONObject {
        val json = JSONObject()
        json.put("username", this.username)
        return json
    }
}