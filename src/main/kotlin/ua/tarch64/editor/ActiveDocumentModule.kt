package ua.tarch64.editor

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.util.messages.MessageBusConnection
import ua.tarch64.dispatcher.events.editor.EditorActiveDocumentChangedEvent
import ua.tarch64.plugin.PluginModule
import ua.tarch64.plugin.PluginState
import ua.tarch64.shared.ModuleInjector
import ua.tarch64.shared.helpers.ApplicationHelper
import ua.tarch64.shared.helpers.DocumentHelper
import ua.tarch64.shared.helpers.ProjectHelper

class ActiveDocumentModule: PluginModule() {
    private lateinit var connection: MessageBusConnection
    private val state: PluginState = ModuleInjector.inject()

    override fun up() {
        this.connection = ApplicationManager.getApplication().messageBus.connect()

        this.connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, object: FileEditorManagerListener {
            override fun selectionChanged(event: FileEditorManagerEvent) {
                val document: Document? = event.newFile?.let(DocumentHelper::document)
                this@ActiveDocumentModule.onDocumentSelected(document)
            }
        })

        this.activeDocument()?.let(this::onDocumentSelected)
    }

    private fun onDocumentSelected(document: Document?) {
        this.state.activeDocument = document
        val event = EditorActiveDocumentChangedEvent()
        this.dispatcher.trigger(event)
    }

    private fun activeDocument(): Document? {
        return ApplicationHelper.runEventAction {
            FileEditorManager.getInstance(ProjectHelper.active()).selectedTextEditor?.document
        }
    }

    override fun down() {
        this.connection.disconnect()
    }
}