package ua.tarch64.documentListener

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.util.messages.MessageBusConnection
import ua.tarch64.documentListener.events.ActiveDocumentChangedEvent
import ua.tarch64.documentListener.events.DocumentsListenerEvents
import ua.tarch64.shared.events.Events
import ua.tarch64.shared.events.models.IEvents
import ua.tarch64.shared.moduleInjection.IInjectionModule
import ua.tarch64.shared.moduleInjection.InjectionModule

class DocumentsListener: IEvents by Events(), IInjectionModule by InjectionModule() {
    private val fileEditorHandler = FileEditorHandler(this)
    private var connection: MessageBusConnection? = null

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



