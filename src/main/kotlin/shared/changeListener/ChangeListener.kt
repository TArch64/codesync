package shared.changeListener

import com.intellij.openapi.editor.Document
import shared.changeListener.events.ChangeListenerEvents
import shared.changeListener.events.DocumentChanged
import shared.events.EventsManager

class ChangeListener: EventsManager() {
    private var activeDocument: Document? = null
    private val changeDocumentHandler = ChangeDocumentHandler(this)

    fun changeActiveDocument(document: Document?) {
        this.removeDocumentListener()
        this.activeDocument = document
        this.addDocumentListener()
    }

    private fun removeDocumentListener() {
        this.activeDocument?.removeDocumentListener(this.changeDocumentHandler)
    }

    private fun addDocumentListener() {
        this.activeDocument?.addDocumentListener(this.changeDocumentHandler)
    }

    fun onDocumentChanged(handler: (event: DocumentChanged) -> Unit) {
        this.on(ChangeListenerEvents.DOCUMENT_CHANGED.name, handler)
    }
}