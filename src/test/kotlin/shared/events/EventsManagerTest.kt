package shared.events

import getPrivateField
import org.junit.jupiter.api.Test
import shared.events.models.Event
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class EventsTest_on {
    @Test fun addedNewSubscription() {
        val eventsManager = Events()
        eventsManager.on<Event>("test") { }
        val subscriptions: MutableList<*> = getPrivateField(eventsManager, "subscriptions") as MutableList<*>
        assertEquals(subscriptions.size, 1, "New subscriptions successfully added")
    }
}

internal class EventsTest_off {
    @Test fun removedSubscription() {
        val eventsManager = Events()
        eventsManager.on<Event>("test") { }.off()
        val subscriptions: MutableList<*> = getPrivateField(eventsManager, "subscriptions") as MutableList<*>
        assertTrue(subscriptions.isEmpty(), "Subscription successfully removed")
    }
}

internal class EventsTest_trigger {
    private fun handleEvent(event: Event) {
        assertEquals(event.name, "test")
    }

    @Test fun subscriptionTriggered() {
        val eventsManager = Events()
        eventsManager.on<Event>("test", this::handleEvent)
        eventsManager.trigger(Event("test"))
    }

    @Test fun skippedRemovedSubscription() {
    }
}
