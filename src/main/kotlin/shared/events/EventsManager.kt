package shared.events

class EventsManager {
    private val subscriptions: MutableList<Subscription> = mutableListOf()

    fun on(eventName: String, handler: (payload: Any?) -> Unit): Subscription {
        val subscription = Subscription(eventName, handler, this::off)
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
