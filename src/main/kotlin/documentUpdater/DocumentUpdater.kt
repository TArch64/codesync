package documentUpdater

import shared.helpers.ApplicationHelper
import shared.models.DocumentChanges

class DocumentUpdater {
    fun updateDocument(changes: DocumentChanges) {
        val document = changes.fetchDocument()

        ApplicationHelper.runWriteAction {
            document.replaceString(changes.startChangesAtPosition, changes.endChangesAtPosition, changes.changed)
        }
    }
}