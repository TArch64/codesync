package documentUpdater

import shared.State
import shared.helpers.ApplicationHelper
import shared.models.DocumentChanges

class DocumentUpdater {
    fun updateDocument(changes: DocumentChanges) {
        if ( State.lastDocumentChanges?.isEqual(changes) == true ) { return }

        val document = changes.fetchDocument()

        ApplicationHelper.runWriteAction {
            document.replaceString(changes.startChangesAtPosition, changes.endChangesAtPosition, changes.changed)
        }
    }
}