package ua.tarch64.shared.dispatcher.events

import ua.tarch64.shared.dispatcher.Event

class PluginUpEvent: Event(NAME) {
    companion object { const val NAME = "plugin::up" }
}
