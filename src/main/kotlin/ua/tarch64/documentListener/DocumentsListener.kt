package ua.tarch64.documentListener

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.util.messages.MessageBusConnection
import ua.tarch64.documentListener.events.ActiveDocumentChangedEvent
import ua.tarch64.documentListener.events.DocumentsListenerEvents
import ua.tarch64.shared.events.Events

class DocumentsListener: Events() {
    private var connection: MessageBusConnection? = null
    private val fileEditorHandler = FileEditorHandler(this)

    fun up() {
        this.connection = ApplicationManager.getApplication().messageBus.connect()
        this.connection?.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, this.fileEditorHandler)
    }

    fun down() {
        this.connection?.disconnect()
    }

    fun onActiveDocumentChange(handler: (event: ActiveDocumentChangedEvent) -> Unit) {
        this.on(DocumentsListenerEvents.ACTIVE_DOCUMENT_CHANGED.name, handler)
    }
}



