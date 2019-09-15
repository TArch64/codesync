package ua.tarch64.shared.events.models

class Subscription<T: Event> constructor(
    private val eventName: String,
    private val handler: (event: T) -> Unit
) {
    fun of(eventName: String): Boolean {
        return this.eventName == eventName
    }

    fun handle(event: T) {
        this.handler(event)
    }
}