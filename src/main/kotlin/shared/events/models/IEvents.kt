package shared.events.models

interface IEvents {
    fun <T: Event> on(eventName: String, handler: (event: T) -> Unit): Subscription<Event>
    fun trigger(event: Event)
}