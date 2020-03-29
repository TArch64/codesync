package ua.tarch64.shared.dispatcher.events.rooms

import ua.tarch64.shared.dispatcher.Event

class RoomLeaveEvent: Event(NAME) {
    companion object { const val NAME = "rooms::leave" }
}
