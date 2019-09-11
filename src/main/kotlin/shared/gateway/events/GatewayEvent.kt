package shared.gateway.events

import org.json.JSONObject
import shared.events.models.Event

data class GatewayEvent(override val name: String, val payload: JSONObject?): Event(name)
