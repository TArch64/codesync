package ua.tarch64.documentListener

import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.vfs.VirtualFile
import ua.tarch64.shared.dispatcher.Dispatcher
import ua.tarch64.shared.dispatcher.events.ChangedActiveDocumentEvent
import ua.tarch64.shared.helpers.DocumentHelper
import ua.tarch64.shared.moduleInjection.IInjectionModule
import ua.tarch64.shared.moduleInjection.InjectionModule

class FileEditorHandler: FileEditorManagerListener, IInjectionModule by InjectionModule() {
    private val dispatcher = this.injectModule(Dispatcher::class.java)

    override fun selectionChanged(event: FileEditorManagerEvent) {
        val activeDocumentChange = ChangedActiveDocumentEvent(this.fileToDocument(event.newFile))
        this.dispatcher.trigger(activeDocumentChange)
    }

    private fun fileToDocument(file: VirtualFile?): Document? {
        return if (file == null) null else DocumentHelper.document(file)
    }
}