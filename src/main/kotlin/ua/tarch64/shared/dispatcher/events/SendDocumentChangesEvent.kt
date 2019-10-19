package ua.tarch64.shared.dispatcher.events

import ua.tarch64.shared.dispatcher.Event
import ua.tarch64.shared.models.DocumentChanges

class SendDocumentChangesEvent(changes: DocumentChanges): Event(NAME, changes) {
    companion object { const val NAME = "document::sendChanges" }
}