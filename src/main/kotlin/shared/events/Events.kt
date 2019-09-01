package shared.events

import shared.events.models.Event
import shared.events.models.IEvents
import shared.events.models.Subscription

open class Events: IEvents {
    private val subscriptions: MutableList<Subscription<Event>> = mutableListOf()

    override fun <T: Event> on(eventName: String, handler: (event: T) -> Unit): Subscription<Event> {
        val subscription = Subscription(eventName, handler as (event: Event) -> Unit, this::off)
        this.subscriptions.add(subscription)
        return subscription
    }

    private fun off(subscription: Subscription<Event>) {
        this.subscriptions.remove(subscription)
    }

    override fun trigger(event: Event) {
        this.subscriptionsOf(event).forEach { it.handle(event) }
    }

    private fun subscriptionsOf(event: Event): List<Subscription<Event>> {
        return this.subscriptions.filter { it.of(event.name) }
    }
}
