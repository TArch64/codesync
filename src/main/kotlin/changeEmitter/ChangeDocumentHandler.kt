package changeEmitter

import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import changeEmitter.events.ActiveDocumentChangedEvent
import shared.helpers.DocumentHelper
import shared.models.DocumentChanges
import shared.State

class ChangeDocumentHandler(private val emitter: ChangesEmitter): DocumentListener {
    override fun beforeDocumentChange(event: DocumentEvent) {
        val changes = DocumentChanges(
            event.newFragment.toString(),
            DocumentHelper.relativePath(event.document),
            event.offset,
            event.offset + event.oldLength
        )

        if ( State.lastDocumentChanges?.isEqual(changes) == true ) { return }

        State.lastDocumentChanges = changes
        this.handleUserChanges(changes)
    }

    private fun handleUserChanges(changes: DocumentChanges) {
        val documentChanged = ActiveDocumentChangedEvent(changes)
        this.emitter.trigger(documentChanged)
    }
}