package shared.documentListener.events

import com.intellij.openapi.editor.Document

data class ActiveDocumentChange(val oldDocument: Document?, val newDocument: Document?)