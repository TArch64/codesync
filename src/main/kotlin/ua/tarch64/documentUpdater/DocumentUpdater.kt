package ua.tarch64.documentUpdater

import ua.tarch64.plugin.Plugin
import ua.tarch64.shared.helpers.ApplicationHelper
import ua.tarch64.shared.models.DocumentChanges

class DocumentUpdater {
    fun updateDocument(changes: DocumentChanges) {
        if ( Plugin.instance.state.lastDocumentChanges?.isEqual(changes) == true ) { return }

        val document = changes.fetchDocument()

        ApplicationHelper.runWriteAction {
            document.replaceString(changes.startChangesAtPosition, changes.endChangesAtPosition, changes.changed)
        }
    }
}