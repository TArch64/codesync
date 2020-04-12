package ua.tarch64.editor.text

import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import ua.tarch64.dispatcher.Event
import ua.tarch64.dispatcher.events.editor.EditorActiveDocumentChangedEvent
import ua.tarch64.dispatcher.events.editor.EditorOutputTextChangesEvent
import ua.tarch64.dispatcher.events.editor.EditorOutputTextChangesEventPayload
import ua.tarch64.plugin.PluginModule
import ua.tarch64.plugin.PluginState
import ua.tarch64.shared.ModuleInjector
import ua.tarch64.shared.models.TextChanges

class OutputTextModule: PluginModule() {
    private val state: PluginState = ModuleInjector.inject()
    private val textChangesHandler: DocumentListener = object: DocumentListener {
        override fun beforeDocumentChange(event: DocumentEvent) {
            val changes = TextChanges.fromDocumentEvent(event)
            this@OutputTextModule.onTextChanges(changes)
        }
    }

    override fun up() {
        this.dispatcher.listen(EditorActiveDocumentChangedEvent.NAME).subscribe(this::onActiveDocumentChanged)
    }

    fun onActiveDocumentChanged(event: Event) {
        this.removeTextChangesListener()
        this.listenTextChanges()
    }

    private fun listenTextChanges() {
        this.state.activeDocument?.addDocumentListener(this.textChangesHandler)
    }

    private fun onTextChanges(changes: TextChanges) {
        if ( this.state.lastTextChanges?.isEqual(changes) == true ) { return }
        this.state.lastTextChanges = changes
        val event = EditorOutputTextChangesEvent(EditorOutputTextChangesEventPayload(
                changes = changes
        ))
        this.dispatcher.trigger(event)
    }

    private fun removeTextChangesListener() {
        this.state.previousActiveDocument?.removeDocumentListener(this.textChangesHandler)
    }
}
