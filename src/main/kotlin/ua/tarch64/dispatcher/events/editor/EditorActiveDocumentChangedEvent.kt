package ua.tarch64.dispatcher.events.editor

import ua.tarch64.dispatcher.Event

class EditorActiveDocumentChangedEvent: Event(NAME) {
    companion object { const val NAME = "editor::active:document::changed" }
}