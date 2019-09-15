package ua.tarch64.documentListener

import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.vfs.VirtualFile
import ua.tarch64.documentListener.events.ActiveDocumentChangedEvent
import ua.tarch64.shared.helpers.DocumentHelper

class FileEditorHandler(private val listener: DocumentsListener): FileEditorManagerListener {
    override fun selectionChanged(event: FileEditorManagerEvent) {
        val activeDocumentChange = ActiveDocumentChangedEvent(this.fileToDocument(event.newFile))
        this.listener.trigger(activeDocumentChange)
    }

    private fun fileToDocument(file: VirtualFile?): Document? {
        return when {
            file != null -> DocumentHelper.document(file)
            else -> null
        }
    }
}