package ua.tarch64.shared.dispatcher.events.rooms

import org.json.JSONObject
import ua.tarch64.shared.dispatcher.Event

class RoomCreatedEvent(payload: RoomCreatedEventPayload): Event(NAME, payload) {
    companion object {
        const val NAME = "rooms::created"

        fun fromJSON(json: JSONObject): RoomCreatedEvent {
            return RoomCreatedEvent(RoomCreatedEventPayload(
                roomId = json.getString("roomId")
            ))
        }
    }
}

data class RoomCreatedEventPayload(val roomId: String)