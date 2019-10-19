package ua.tarch64.shared.dispatcher.events

import com.intellij.openapi.editor.Document
import ua.tarch64.shared.dispatcher.Event

class ChangedActiveDocumentEvent(document: Document?): Event(NAME, document) {
    companion object { const val NAME = "document::activeChanged" }
}