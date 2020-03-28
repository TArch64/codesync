package ua.tarch64.shared.dispatcher.events.rooms

import org.json.JSONObject
import ua.tarch64.shared.dispatcher.Event

class RoomJoinedEvent(payload: RoomJoinedEventPayload): Event(NAME, payload) {
    companion object {
        const val NAME = "rooms::joined"

        fun fromJSON(json: JSONObject): RoomJoinedEvent {
            return RoomJoinedEvent(RoomJoinedEventPayload(
                roomId = json.getString("roomId")
            ))
        }
    }
}

data class RoomJoinedEventPayload(val roomId: String)
