package ua.tarch64.documentListener

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.util.messages.MessageBusConnection
import ua.tarch64.plugin.PluginModule
import ua.tarch64.shared.ModuleInjector
import ua.tarch64.shared.helpers.ApplicationHelper
import ua.tarch64.shared.helpers.ProjectHelper

class DocumentsListener: PluginModule() {
    private val fileEditorHandler: FileEditorHandler = ModuleInjector.inject()
    private lateinit var connection: MessageBusConnection

    override fun up() {
        this.connection = ApplicationManager.getApplication().messageBus.connect()
        this.connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, this.fileEditorHandler)

        val activeDocument = this.activeDocument()
        if ( activeDocument != null ) this.fileEditorHandler.onDocumentSelected(activeDocument)
    }

    private fun activeDocument(): Document? {
        return ApplicationHelper.runEventAction {
            FileEditorManager.getInstance(ProjectHelper.active()).selectedTextEditor!!.document
        }
    }

    override fun down() {
        this.connection.disconnect()
    }
}



