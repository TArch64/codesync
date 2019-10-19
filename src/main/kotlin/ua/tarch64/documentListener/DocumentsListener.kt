package ua.tarch64.documentListener

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.util.messages.MessageBusConnection
import ua.tarch64.plugin.PluginModule

class DocumentsListener: PluginModule() {
    private val fileEditorHandler = this.injectModule(FileEditorHandler::class.java)
    private lateinit var connection: MessageBusConnection

    override fun up() {
        this.connection = ApplicationManager.getApplication().messageBus.connect()
        this.connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, this.fileEditorHandler)
    }

    override fun down() {
        this.connection.disconnect()
    }
}



