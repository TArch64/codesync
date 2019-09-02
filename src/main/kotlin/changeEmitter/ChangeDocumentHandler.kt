package changeEmitter

import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import changeEmitter.events.DocumentChangedEvent

class ChangeDocumentHandler(private val emitter: ChangesEmitter): DocumentListener {
    override fun beforeDocumentChange(event: DocumentEvent) {
        val documentChanged = DocumentChangedEvent(event.newFragment.toString())
        this.emitter.trigger(documentChanged)
    }
}