package ua.tarch64.dispatcher.events.editor

import ua.tarch64.dispatcher.Event
import ua.tarch64.shared.models.TextChanges

class EditorOutputTextChangesEvent(payload: EditorOutputTextChangesEventPayload): Event(NAME, payload) {
    companion object { const val NAME = "editor::changed" }
}

data class EditorOutputTextChangesEventPayload(
        val changes: TextChanges
)