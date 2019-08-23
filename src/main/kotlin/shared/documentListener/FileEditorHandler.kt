package shared.documentListener

import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileEditorManagerAdapter
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.vfs.VirtualFile
import shared.documentListener.events.ActiveDocumentChange
import shared.documentListener.events.DocumentsListenerEvents
import shared.helpers.FileHelper

class FileEditorHandler(private val listener: DocumentsListener): FileEditorManagerAdapter() {
    override fun selectionChanged(event: FileEditorManagerEvent) {
        val activeDocumentChange = ActiveDocumentChange(
            newDocument = this.fileToDocument(event.newFile),
            oldDocument = this.fileToDocument(event.oldFile)
        )
        this.listener.trigger(DocumentsListenerEvents.ACTIVE_DOCUMENT_CHANGE.name, activeDocumentChange)
    }

    private fun fileToDocument(file: VirtualFile?): Document? {
        return when {
            file != null -> FileHelper.document(file)
            else -> null
        }
    }
}