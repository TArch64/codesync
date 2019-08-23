package shared.events

open class EventsManager {
    private val subscriptions: MutableList<Subscription> = mutableListOf()

    fun <Payload> on(eventName: String, handler: (payload: Payload) -> Unit): Subscription {
        val subscription = Subscription(eventName, handler as (Any?) -> Unit, this::off)
        this.subscriptions.add(subscription)
        return subscription
    }

    fun trigger(eventName: String, payload: Any?) {
        subscriptions.filter { it.of(eventName) }.forEach { it.handle(payload) }
    }

    private fun off(subscription: Subscription) {
        subscriptions.remove(subscription)
    }
}
