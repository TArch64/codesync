package shared.gateway.events

import shared.events.models.Event
import shared.models.Jsonable

data class GatewayEvent(val eventName: String, val payload: Jsonable?): Event(eventName)