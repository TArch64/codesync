package ua.tarch64.dispatcher.events.plugin

import org.json.JSONObject
import ua.tarch64.dispatcher.Event

class PluginErrorEvent(payload: PluginErrorEventPayload): Event(NAME, payload) {
    companion object {
        const val NAME = "common::error"

        fun fromJSON(json: JSONObject): PluginErrorEvent {
            return PluginErrorEvent(PluginErrorEventPayload(
                description = json.getString("description")
            ))
        }
    }
}

data class PluginErrorEventPayload(val description: String)
