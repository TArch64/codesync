package ua.tarch64.dispatcher.events

import ua.tarch64.dispatcher.Event

class PluginUpEvent: Event(NAME) {
    companion object { const val NAME = "plugin::up" }
}
