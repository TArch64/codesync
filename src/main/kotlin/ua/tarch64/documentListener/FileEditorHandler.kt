package ua.tarch64.documentListener

import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.vfs.VirtualFile
import ua.tarch64.shared.dispatcher.Dispatcher
import ua.tarch64.shared.dispatcher.events.ChangedActiveDocumentEvent
import ua.tarch64.shared.helpers.DocumentHelper
import ua.tarch64.shared.ModuleInjector

class FileEditorHandler: FileEditorManagerListener {
    private val dispatcher: Dispatcher = ModuleInjector.inject()

    override fun selectionChanged(event: FileEditorManagerEvent) {
        val activeDocumentChange = ChangedActiveDocumentEvent(this.fileToDocument(event.newFile))
        this.dispatcher.trigger(activeDocumentChange)
    }

    private fun fileToDocument(file: VirtualFile?): Document? {
        return if (file == null) null else DocumentHelper.document(file)
    }
}