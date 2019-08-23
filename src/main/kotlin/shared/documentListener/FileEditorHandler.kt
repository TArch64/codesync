package shared.documentListener

import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileEditorManagerAdapter
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.vfs.VirtualFile
import shared.documentListener.events.ActiveDocumentChange
import shared.helpers.FileHelper

class FileEditorHandler(private val listener: DocumentsListener): FileEditorManagerAdapter() {
    override fun selectionChanged(event: FileEditorManagerEvent) {
        val activeDocumentChange = ActiveDocumentChange(
            this.fileToDocument(event.oldFile),
            this.fileToDocument(event.newFile)
        )
        this.listener.trigger(activeDocumentChange)
    }

    private fun fileToDocument(file: VirtualFile?): Document? {
        return when {
            file != null -> FileHelper.document(file)
            else -> null
        }
    }
}