package shared.events

class Subscription constructor(
    private val eventName: String,
    private val handler: (payload: Any?) -> Unit,
    private val unsubscribe: (subscription: Subscription) -> Unit
) {
    fun of(eventName: String): Boolean {
        return this.eventName == eventName
    }

    fun off() {
        this.unsubscribe(this)
    }

    fun handle(payload: Any?) {
        this.handler(payload)
    }
}