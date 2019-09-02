package shared.gateway.events

import org.json.JSONObject
import shared.events.models.Event

data class GatewayEvent(val eventName: String, val payload: JSONObject?): Event(eventName)
