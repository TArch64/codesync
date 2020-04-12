package ua.tarch64.editor.text

import ua.tarch64.dispatcher.Event
import ua.tarch64.dispatcher.events.editor.EditorInputTextChangesEvent
import ua.tarch64.dispatcher.events.editor.EditorInputTextChangesEventPayload
import ua.tarch64.plugin.PluginModule
import ua.tarch64.plugin.PluginState
import ua.tarch64.shared.ModuleInjector
import ua.tarch64.shared.helpers.ApplicationHelper

class InputTextModule: PluginModule() {
    private val state: PluginState = ModuleInjector.inject()

    override fun up() {
        this.dispatcher.listen(EditorInputTextChangesEvent.NAME).subscribe(this::onChanges)
    }

    private fun onChanges(event: Event) {
        val changes = (event.payload as EditorInputTextChangesEventPayload).changes
        if (this.state.lastTextChanges?.isEqual(changes) == true) { return }
        val document = changes.fetchDocument()
        ApplicationHelper.runWriteAction {
            document.replaceString(
                    changes.startChangesAtPosition,
                    changes.endChangesAtPosition,
                    changes.changed
            )
        }
    }
}
