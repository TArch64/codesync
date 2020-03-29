package ua.tarch64.dispatcher.events.rooms

import org.json.JSONObject
import ua.tarch64.dispatcher.Event

class RoomCollaboratorJoinedEvent(payload: RoomCollaboratorJoinedEventPayload): Event(NAME, payload) {
    companion object {
        const val NAME = "rooms::collaborator::joined"

        fun fromJSON(json: JSONObject): RoomCollaboratorJoinedEvent {
            return RoomCollaboratorJoinedEvent(RoomCollaboratorJoinedEventPayload(
                username = json.getString("username")
            ))
        }
    }
}

data class RoomCollaboratorJoinedEventPayload(val username: String);