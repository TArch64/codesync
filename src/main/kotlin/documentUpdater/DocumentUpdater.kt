package documentUpdater

import plugin.Plugin
import shared.helpers.ApplicationHelper
import shared.models.DocumentChanges

class DocumentUpdater {
    fun updateDocument(changes: DocumentChanges) {
        if ( Plugin.instance.state.lastDocumentChanges?.isEqual(changes) == true ) { return }

        val document = changes.fetchDocument()

        ApplicationHelper.runWriteAction {
            document.replaceString(changes.startChangesAtPosition, changes.endChangesAtPosition, changes.changed)
        }
    }
}