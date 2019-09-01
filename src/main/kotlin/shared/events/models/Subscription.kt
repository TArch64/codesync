package shared.events.models

class Subscription<T: Event> constructor(
    private val eventName: String,
    private val handler: (event: T) -> Unit,
    private val unsubscribe: (subscription: Subscription<T>) -> Unit
) {
    fun of(eventName: String): Boolean {
        return this.eventName == eventName
    }

    fun off() {
        this.unsubscribe(this)
    }

    fun handle(event: T) {
        this.handler(event)
    }
}