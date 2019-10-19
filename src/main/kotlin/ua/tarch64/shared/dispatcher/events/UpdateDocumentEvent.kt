package ua.tarch64.shared.dispatcher.events

import ua.tarch64.shared.dispatcher.Event
import ua.tarch64.shared.models.DocumentChanges

class UpdateDocumentEvent(documentChanges: DocumentChanges): Event(NAME, documentChanges) {
    companion object { const val NAME = "document::update" }
}
