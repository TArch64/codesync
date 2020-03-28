package ua.tarch64.shared.dispatcher.events

import ua.tarch64.shared.dispatcher.Event

class CreateRoomEvent(username: String): Event(NAME, username) {
    companion object { const val NAME = "rooms::create" }
}