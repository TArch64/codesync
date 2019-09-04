package shared.models

import com.intellij.openapi.editor.Document
import org.json.JSONObject
import shared.helpers.DocumentHelper

data class DocumentChanges(val changed: String, val documentRelativePath: String) {
    fun fetchDocument(): Document {
        return DocumentHelper.documentByRelativePath(this.documentRelativePath)
    }

    companion object {
        fun fromJSON(json: JSONObject): DocumentChanges {
            return DocumentChanges(
                json.getString("changed"),
                json.getString("documentRelativePath")
            )
        }
    }
}