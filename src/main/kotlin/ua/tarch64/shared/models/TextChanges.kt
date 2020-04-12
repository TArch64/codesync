package ua.tarch64.shared.models

import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.event.DocumentEvent
import org.json.JSONObject
import ua.tarch64.shared.helpers.DocumentHelper

data class TextChanges(
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

    fun isEqual(changes: TextChanges): Boolean {
        return changes.changed == this.changed
                && changes.startChangesAtPosition == this.startChangesAtPosition
                && changes.endChangesAtPosition == this.endChangesAtPosition
    }

    companion object {
        fun fromJSON(json: JSONObject): TextChanges {
            return TextChanges(
                json.getString("changed"),
                json.getString("documentRelativePath"),
                json.getInt("startChangesAtPosition"),
                json.getInt("endChangesAtPosition")
            )
        }

        fun fromDocumentEvent(event: DocumentEvent): TextChanges {
            return TextChanges(
                    event.newFragment.toString(),
                    DocumentHelper.relativePath(event.document),
                    event.offset,
                    event.offset + event.oldLength
            )
        }
    }
}