package shared.events

import getPrivateField
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class EventsManagerTest_on {
    @Test fun addedNewSubscription() {
        val eventsManager = EventsManager()
        eventsManager.on("test") { }
        val subscriptions: MutableList<*> = getPrivateField(eventsManager, "subscriptions") as MutableList<*>
        assertEquals(subscriptions.size, 1, "New subscriptions successfully added")
    }
}

internal class EventsManagerTest_off {
    @Test fun removedSubscription() {
        val eventsManager = EventsManager()
        eventsManager.on("test") { }.off()
        val subscriptions: MutableList<*> = getPrivateField(eventsManager, "subscriptions") as MutableList<*>
        assertTrue(subscriptions.isEmpty(), "Subscription successfully removed")
    }
}

internal class EventsManagerTest_trigger {
    fun handleEvent(payload: Any?) {
        assertEquals(payload, "Subscription triggered")
    }

    @Test fun subscriptionTriggered() {
        val eventsManager = EventsManager()
        eventsManager.on("test", this::handleEvent)
        eventsManager.trigger("test", "Subscription triggered")
    }

    @Test fun skippedRemovedSubscription() {
    }
}
