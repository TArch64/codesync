package ua.tarch64.dispatcher.events.rooms

import org.json.JSONObject
import ua.tarch64.dispatcher.Event

class RoomJoinEvent(payload: RoomJoinEventPayload): Event(NAME, payload) {
    companion object { const val NAME = "rooms::join" }
}

data class RoomJoinEventPayload(
    val username: String,
    val roomId: String
) {
    fun toJSON(): JSONObject {
        val json = JSONObject()
        json.put("username", this.username)
        json.put("roomId", this.roomId)
        return json
    }
}