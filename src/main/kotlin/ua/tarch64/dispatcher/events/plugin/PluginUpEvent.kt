package ua.tarch64.dispatcher.events.plugin

import ua.tarch64.dispatcher.Event

class PluginUpEvent: Event(NAME) {
    companion object { const val NAME = "common::up" }
}
