package ua.tarch64.dispatcher.events

import ua.tarch64.dispatcher.Event
import ua.tarch64.shared.models.DocumentChanges

class UpdateDocumentEvent(documentChanges: DocumentChanges): Event(NAME, documentChanges) {
    companion object { const val NAME = "document::update" }
}
