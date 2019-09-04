package shared.helpers

import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.ResourceUtil
import shared.ui.Notifications
import java.net.URL

object DocumentHelper {
    fun document(file: VirtualFile): Document {
        return FileDocumentManager.getInstance().getDocument(file)!!
    }

    fun documentByRelativePath(path: String): Document {
        val virtualFile = VfsUtil.findFileByURL(urlFromPath(path))
        return document(virtualFile!!)
    }

    private fun urlFromPath(path: String): URL {
        val paths = path.split('/').toList()
        val dirPath = paths.dropLast(1).reduce { result, dir -> "$result/$dir" }
        return ResourceUtil.getResource(javaClass, dirPath, paths.last())
    }

    fun relativePath(document: Document): String {
        val file = this.file(document)
        return VfsUtilCore.getRelativePath(file, this.fileRoot(file))!!
    }

    private fun file(document: Document): VirtualFile {
        return FileDocumentManager.getInstance().getFile(document)!!
    }

    private fun fileRoot(file: VirtualFile): VirtualFile {
        val contentRoot = ProjectFileIndex.SERVICE.getInstance(ProjectHelper.active()).getContentRootForFile(file)

        return when (contentRoot) {
            null -> throw Exception("Content Root Must exist")
            else -> contentRoot
        }
    }
}