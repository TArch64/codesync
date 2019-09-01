package shared.models

import org.json.JSONObject

class Jsonable(public val payload: Any) {

    fun toJSON(): JSONObject {
        return JSONObject()
    }
}