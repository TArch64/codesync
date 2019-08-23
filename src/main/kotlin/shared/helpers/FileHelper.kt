package shared.helpers

import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.vfs.VirtualFile

class FileHelper {
    companion object {
        fun document(file: VirtualFile): Document {
            return FileDocumentManager.getInstance().getDocument(file)!!
        }
    }
}