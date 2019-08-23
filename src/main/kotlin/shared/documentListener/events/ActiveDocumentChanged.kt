package shared.documentListener.events

import com.intellij.openapi.editor.Document
import shared.events.Event

data class ActiveDocumentChanged(val document: Document?): Event(DocumentsListenerEvents.ACTIVE_DOCUMENT_CHANGED.name)