package ua.tarch64.documentListener

import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.vfs.VirtualFile
import ua.tarch64.documentListener.events.ActiveDocumentChangedEvent
import ua.tarch64.shared.helpers.DocumentHelper
import ua.tarch64.shared.moduleInjection.IInjectionModule
import ua.tarch64.shared.moduleInjection.InjectionModule

class FileEditorHandler(private val documentsListener: DocumentsListener): FileEditorManagerListener, IInjectionModule by InjectionModule() {
    override fun selectionChanged(event: FileEditorManagerEvent) {
        val activeDocumentChange = ActiveDocumentChangedEvent(this.fileToDocument(event.newFile))
        this.documentsListener.trigger(activeDocumentChange)
    }

    private fun fileToDocument(file: VirtualFile?): Document? {
        return if (file == null) null else DocumentHelper.document(file)
    }
}