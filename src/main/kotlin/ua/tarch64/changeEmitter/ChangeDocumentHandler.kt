package ua.tarch64.changeEmitter

import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import ua.tarch64.plugin.PluginState
import ua.tarch64.shared.dispatcher.Dispatcher
import ua.tarch64.shared.dispatcher.events.SendDocumentChangesEvent
import ua.tarch64.shared.helpers.DocumentHelper
import ua.tarch64.shared.models.DocumentChanges
import ua.tarch64.shared.moduleInjection.InjectionModule

class ChangeDocumentHandler: InjectionModule(), DocumentListener {
    private val pluginState = this.injectModule(PluginState::class.java)
    private val dispatcher = this.injectModule(Dispatcher::class.java)

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