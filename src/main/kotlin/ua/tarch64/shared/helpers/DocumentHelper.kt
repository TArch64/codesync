package ua.tarch64.shared.helpers

import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile

object DocumentHelper {
    fun document(file: VirtualFile): Document {
        return ApplicationHelper.runReadAction { FileDocumentManager.getInstance().getDocument(file) }!!
    }

    fun documentByRelativePath(path: String): Document {
        val virtualFile = VfsUtilCore.findRelativeFile(path, ProjectHelper.rootDir())
        return document(virtualFile!!)
    }

    fun relativePath(document: Document): String {
        return VfsUtilCore.getRelativePath(this.file(document), ProjectHelper.rootDir())!!
    }

    private fun file(document: Document): VirtualFile {
        return FileDocumentManager.getInstance().getFile(document)!!
    }
}