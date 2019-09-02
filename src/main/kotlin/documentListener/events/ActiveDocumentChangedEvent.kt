package documentListener.events

import com.intellij.openapi.editor.Document
import shared.events.models.Event

data class ActiveDocumentChangedEvent(val document: Document?): Event(DocumentsListenerEvents.ACTIVE_DOCUMENT_CHANGED.name)