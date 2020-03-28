package ua.tarch64

import ua.tarch64.plugin.PluginModule
import ua.tarch64.plugin.PluginState
import ua.tarch64.shared.ModuleInjector
import ua.tarch64.shared.dispatcher.Event
import ua.tarch64.shared.dispatcher.events.UpdateDocumentEvent
import ua.tarch64.shared.helpers.ApplicationHelper
import ua.tarch64.shared.models.DocumentChanges

class DocumentUpdater: PluginModule() {
    private val pluginState: PluginState = ModuleInjector.inject()

    override fun down() = Unit
    override fun up() {
        this.dispatcher.listen(UpdateDocumentEvent.NAME).subscribe(this::updateDocument)
    }

    private fun updateDocument(event: Event) {
        val changes = event.payload as DocumentChanges

        if ( this.pluginState.lastDocumentChanges?.isEqual(changes) == true ) return

        val document = changes.fetchDocument()

        ApplicationHelper.runWriteAction {
            document.replaceString(changes.startChangesAtPosition, changes.endChangesAtPosition, changes.changed)
        }
    }
}