package ua.tarch64.dispatcher.events

import ua.tarch64.dispatcher.Event
import ua.tarch64.shared.models.DocumentChanges

class SendDocumentChangesEvent(changes: DocumentChanges): Event(NAME, changes) {
    companion object { const val NAME = "document::sendChanges" }
}