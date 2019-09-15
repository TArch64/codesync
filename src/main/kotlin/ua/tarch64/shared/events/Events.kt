package ua.tarch64.shared.events

import ua.tarch64.shared.events.models.Event
import ua.tarch64.shared.events.models.IEvents
import ua.tarch64.shared.events.models.Subscription

open class Events: IEvents {
    private val subscriptions: MutableList<Subscription<Event>> = mutableListOf()

    override fun <T: Event> on(eventName: String, handler: (event: T) -> Unit): Subscription<Event> {
        val subscription = Subscription(eventName, handler as (event: Event) -> Unit)
        this.subscriptions.add(subscription)
        return subscription
    }

    override fun trigger(event: Event) {
        this.subscriptionsOf(event).forEach { it.handle(event) }
    }

    private fun subscriptionsOf(event: Event): List<Subscription<Event>> {
        return this.subscriptions.filter { it.of(event.name) }
    }
}
