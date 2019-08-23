package shared.documentListener.events

import com.intellij.openapi.editor.Document
import shared.events.Event

data class ActiveDocumentChange(
    val oldDocument: Document?,
    val newDocument: Document?
): Event(DocumentsListenerEvents.ACTIVE_DOCUMENT_CHANGE.name)