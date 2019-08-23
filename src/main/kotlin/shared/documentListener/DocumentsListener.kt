package shared.documentListener

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import shared.documentListener.events.ActiveDocumentChange
import shared.documentListener.events.DocumentsListenerEvents
import shared.events.EventsManager

class DocumentsListener: EventsManager() {
    private val messagesBus = ApplicationManager.getApplication().messageBus
    private val connection = this.messagesBus.connect()
    private val fileEditorHandler = FileEditorHandler(this)

    init {
        this.connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, this.fileEditorHandler)
    }

    companion object {
        private val instance = DocumentsListener()

        fun onActiveDocumentChange(handler: (event: ActiveDocumentChange) -> Unit) {
            this.instance.on(DocumentsListenerEvents.ACTIVE_DOCUMENT_CHANGE.name, handler)
        }
    }
}



