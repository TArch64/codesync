package shared.models

import com.intellij.openapi.editor.Document
import org.json.JSONObject
import shared.helpers.DocumentHelper

data class DocumentChanges(
    val changed: String,
    val documentRelativePath: String,
    val startChangesAtPosition: Int,
    val endChangesAtPosition: Int
) {
    fun fetchDocument(): Document {
        return DocumentHelper.documentByRelativePath(this.documentRelativePath)
    }

    fun toJSON(): JSONObject {
        val json = JSONObject()
        json.put("changed", this.changed)
        json.put("documentRelativePath", this.documentRelativePath)
        json.put("startChangesAtPosition", this.startChangesAtPosition)
        json.put("endChangesAtPosition", this.endChangesAtPosition)
        return json
    }

    companion object {
        fun fromJSON(json: JSONObject): DocumentChanges {
            return DocumentChanges(
                json.getString("changed"),
                json.getString("documentRelativePath"),
                json.getInt("startChangesAtPosition"),
                json.getInt("endChangesAtPosition")
            )
        }
    }
}