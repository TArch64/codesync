package ua.tarch64.documentListener

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import ua.tarch64.documentListener.events.ActiveDocumentChangedEvent
import ua.tarch64.documentListener.events.DocumentsListenerEvents
import ua.tarch64.shared.events.Events

class DocumentsListener: Events() {
    private val messagesBus = ApplicationManager.getApplication().messageBus
    private val connection = this.messagesBus.connect()
    private val fileEditorHandler = FileEditorHandler(this)

    fun setup() {
        this.connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, this.fileEditorHandler)
    }

    fun onActiveDocumentChange(handler: (event: ActiveDocumentChangedEvent) -> Unit) {
        this.on(DocumentsListenerEvents.ACTIVE_DOCUMENT_CHANGED.name, handler)
    }
}



