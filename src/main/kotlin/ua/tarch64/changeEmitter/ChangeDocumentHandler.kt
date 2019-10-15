package ua.tarch64.changeEmitter

import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import ua.tarch64.changeEmitter.events.ActiveDocumentChangedEvent
import ua.tarch64.plugin.Plugin
import ua.tarch64.shared.helpers.DocumentHelper
import ua.tarch64.shared.models.DocumentChanges

class ChangeDocumentHandler(private val plugin: Plugin, private val emitter: ChangesEmitter): DocumentListener {
    override fun beforeDocumentChange(event: DocumentEvent) {
        val changes = DocumentChanges(
            event.newFragment.toString(),
            DocumentHelper.relativePath(event.document),
            event.offset,
            event.offset + event.oldLength
        )

        if ( this.plugin.state.lastDocumentChanges?.isEqual(changes) == true ) { return }

        this.plugin.state.lastDocumentChanges = changes
        this.handleUserChanges(changes)
    }

    private fun handleUserChanges(changes: DocumentChanges) {
        val documentChanged = ActiveDocumentChangedEvent(changes)
        this.emitter.trigger(documentChanged)
    }
}