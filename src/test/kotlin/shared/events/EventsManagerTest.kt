package shared.events

import getPrivateField
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class EventsManagerTest_on {
    @Test fun addedNewSubscription() {
        val eventsManager = EventsManager()
        eventsManager.on<Event>("test") { }
        val subscriptions: MutableList<*> = getPrivateField(eventsManager, "subscriptions") as MutableList<*>
        assertEquals(subscriptions.size, 1, "New subscriptions successfully added")
    }
}

internal class EventsManagerTest_off {
    @Test fun removedSubscription() {
        val eventsManager = EventsManager()
        eventsManager.on<Event>("test") { }.off()
        val subscriptions: MutableList<*> = getPrivateField(eventsManager, "subscriptions") as MutableList<*>
        assertTrue(subscriptions.isEmpty(), "Subscription successfully removed")
    }
}

internal class EventsManagerTest_trigger {
    private fun handleEvent(event: Event) {
        assertEquals(event.name, "test")
    }

    @Test fun subscriptionTriggered() {
        val eventsManager = EventsManager()
        eventsManager.on<Event>("test", this::handleEvent)
        eventsManager.trigger(Event("test"))
    }

    @Test fun skippedRemovedSubscription() {
    }
}
