package ua.tarch64.shared.dispatcher.events

import ua.tarch64.shared.dispatcher.Event

class RoomCreatedEvent(roomId: String): Event(NAME, roomId) {
    companion object { const val NAME = "rooms::created" }
}