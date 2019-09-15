package ua.tarch64.shared.events.models

interface IEvents {
    fun <T: Event> on(eventName: String, handler: (event: T) -> Unit): Subscription<out Event>
    fun trigger(event: Event)
}