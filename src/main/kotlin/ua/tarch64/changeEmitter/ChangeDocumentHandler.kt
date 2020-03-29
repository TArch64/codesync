package ua.tarch64.changeEmitter

import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import ua.tarch64.plugin.PluginState
import ua.tarch64.dispatcher.Dispatcher
import ua.tarch64.dispatcher.events.SendDocumentChangesEvent
import ua.tarch64.shared.helpers.DocumentHelper
import ua.tarch64.shared.models.DocumentChanges
import ua.tarch64.shared.ModuleInjector

class ChangeDocumentHandler: DocumentListener {
    private val pluginState: PluginState = ModuleInjector.inject()
    private val dispatcher: Dispatcher = ModuleInjector.inject()

    override fun beforeDocumentChange(event: DocumentEvent) {
        val changes = DocumentChanges(
            event.newFragment.toString(),
            DocumentHelper.relativePath(event.document),
            event.offset,
            event.offset + event.oldLength
        )

        if ( this.pluginState.lastDocumentChanges?.isEqual(changes) == true ) { return }

        this.pluginState.lastDocumentChanges = changes
        this.handleUserChanges(changes)
    }

    private fun handleUserChanges(changes: DocumentChanges) {
        this.dispatcher.trigger(SendDocumentChangesEvent(changes))
    }
}