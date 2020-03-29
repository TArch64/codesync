package ua.tarch64.dispatcher.events.rooms

import ua.tarch64.dispatcher.Event

class RoomLeftEvent: Event(NAME) {
    companion object { const val NAME = "rooms::left" }
}
