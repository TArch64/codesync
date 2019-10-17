package ua.tarch64.documentUpdater

import ua.tarch64.plugin.PluginState
import ua.tarch64.shared.helpers.ApplicationHelper
import ua.tarch64.shared.models.DocumentChanges
import ua.tarch64.shared.moduleInjection.InjectionModule

class DocumentUpdater: InjectionModule() {
    private val pluginState = this.injectModule(PluginState::class.java)

    fun updateDocument(changes: DocumentChanges) {
        if ( this.pluginState.lastDocumentChanges?.isEqual(changes) == true ) { return }

        val document = changes.fetchDocument()

        ApplicationHelper.runWriteAction {
            document.replaceString(changes.startChangesAtPosition, changes.endChangesAtPosition, changes.changed)
        }
    }
}