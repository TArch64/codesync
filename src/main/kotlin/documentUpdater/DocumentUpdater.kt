package documentUpdater

import shared.models.DocumentChanges
import shared.ui.Toasts

class DocumentUpdater {
    fun updateDocument(changes: DocumentChanges) {
        val document = changes.fetchDocument()
        Toasts.notifyInfo(document.textLength.toString())
    }
}