package ua.tarch64.shared.dispatcher.events

import ua.tarch64.shared.dispatcher.Event

class CreateRoomEvent: Event(NAME) {
    companion object { const val NAME = "rooms::create" }
}