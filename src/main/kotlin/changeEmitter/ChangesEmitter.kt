package changeEmitter

import com.intellij.openapi.editor.Document
import changeEmitter.events.ChangeListenerEvents
import changeEmitter.events.DocumentChanged
import shared.events.Events

class ChangesEmitter: Events() {
    private var activeDocument: Document? = null
    private val changeDocumentHandler = ChangeDocumentHandler(this)

    init {

    }

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