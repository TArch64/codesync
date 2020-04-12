package ua.tarch64.dispatcher.events.editor

import org.json.JSONObject
import ua.tarch64.dispatcher.Event
import ua.tarch64.shared.models.TextChanges

class EditorInputTextChangesEvent(payload: EditorInputTextChangesEventPayload): Event(NAME, payload) {
    companion object {
        const val NAME = "editor::sync"

        fun fromJSON(json: JSONObject): EditorInputTextChangesEvent {
            return EditorInputTextChangesEvent(EditorInputTextChangesEventPayload(
                    changes = TextChanges.fromJSON(json)
            ))
        }
    }
}

data class EditorInputTextChangesEventPayload(
        val changes: TextChanges
)