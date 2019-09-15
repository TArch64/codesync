package ua.tarch64.documentListener.events

import com.intellij.openapi.editor.Document
import ua.tarch64.shared.events.models.Event

data class ActiveDocumentChangedEvent(
    val document: Document?
): Event(DocumentsListenerEvents.ACTIVE_DOCUMENT_CHANGED.name)