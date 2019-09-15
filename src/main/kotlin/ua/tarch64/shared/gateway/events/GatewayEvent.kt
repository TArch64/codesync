package ua.tarch64.shared.gateway.events

import org.json.JSONObject
import ua.tarch64.shared.events.models.Event

data class GatewayEvent(override val name: String, val payload: JSONObject?): Event(name)
