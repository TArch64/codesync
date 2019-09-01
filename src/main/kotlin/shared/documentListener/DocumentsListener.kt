package shared.documentListener

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import shared.documentListener.events.ActiveDocumentChanged
import shared.documentListener.events.DocumentsListenerEvents
import shared.events.Events

class DocumentsListener: Events() {
    private val messagesBus = ApplicationManager.getApplication().messageBus
    private val connection = this.messagesBus.connect()
    private val fileEditorHandler = FileEditorHandler(this)

    init {
        this.connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, this.fileEditorHandler)
    }

    fun onActiveDocumentChange(handler: (event: ActiveDocumentChanged) -> Unit) {
        this.on(DocumentsListenerEvents.ACTIVE_DOCUMENT_CHANGED.name, handler)
    }
}



