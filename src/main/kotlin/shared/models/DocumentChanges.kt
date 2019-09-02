package shared.models

import org.json.JSONObject

data class DocumentChanges(val changed: String) {
    companion object {
        fun fromJSON(json: JSONObject): DocumentChanges {
            return DocumentChanges(json.getString("changed"))
        }
    }
}