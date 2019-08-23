package shared.changeListener

import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import shared.changeListener.events.DocumentChanged

class ChangeDocumentHandler(val listener: ChangeListener): DocumentListener {
    override fun beforeDocumentChange(event: DocumentEvent) {
        val documentChanged = DocumentChanged(event.newFragment.toString())
        this.listener.trigger(documentChanged)
    }
}