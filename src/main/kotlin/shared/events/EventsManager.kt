package shared.events

open class EventsManager {
    private val subscriptions: MutableList<Subscription<Event>> = mutableListOf()

    fun <T: Event> on(eventName: String, handler: (event: T) -> Unit): Subscription<Event> {
        val subscription = Subscription(eventName, handler as (event: Event) -> Unit, this::off)
        this.subscriptions.add(subscription)
        return subscription
    }

    fun trigger(event: Event) {
        subscriptions.filter { it.of(event.name) }.forEach { it.handle(event) }
    }

    private fun off(subscription: Subscription<Event>) {
        subscriptions.remove(subscription)
    }
}
