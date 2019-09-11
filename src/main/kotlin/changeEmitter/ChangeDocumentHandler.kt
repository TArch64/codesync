package changeEmitter

import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import changeEmitter.events.ActiveDocumentChangedEvent
import shared.helpers.DocumentHelper
import shared.models.DocumentChanges
import shared.ui.Toasts

class ChangeDocumentHandler(private val emitter: ChangesEmitter): DocumentListener {
    override fun beforeDocumentChange(event: DocumentEvent) {
        val documentChanges = DocumentChanges(
            event.newFragment.toString(),
            DocumentHelper.relativePath(event.document),
            event.offset,
            event.offset + event.oldLength
        )
        val documentChanged = ActiveDocumentChangedEvent(documentChanges)
        this.emitter.trigger(documentChanged)
    }
}