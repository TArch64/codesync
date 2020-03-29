package ua.tarch64.dispatcher.events.rooms

import ua.tarch64.dispatcher.Event

class RoomLeaveEvent: Event(NAME) {
    companion object { const val NAME = "rooms::leave" }
}
