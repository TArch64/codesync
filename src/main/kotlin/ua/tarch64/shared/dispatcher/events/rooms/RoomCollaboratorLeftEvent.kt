package ua.tarch64.shared.dispatcher.events.rooms

import org.json.JSONObject
import ua.tarch64.shared.dispatcher.Event

class RoomCollaboratorLeftEvent(payload: RoomCollaboratorLeftEventPayload): Event(NAME, payload) {
    companion object {
        const val NAME = "rooms::collaborator::left"

        fun fromJSON(json: JSONObject): RoomCollaboratorLeftEvent {
            return RoomCollaboratorLeftEvent(RoomCollaboratorLeftEventPayload(
                username = json.getString("username")
            ))
        }
    }
}

data class RoomCollaboratorLeftEventPayload(val username: String);