package ua.tarch64.shared.dispatcher.events

import ua.tarch64.shared.dispatcher.Event

class PluginDownEvent: Event(NAME) {
    companion object { const val NAME = "plugin::down" }
}
