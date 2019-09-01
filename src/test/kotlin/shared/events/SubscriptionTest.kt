package shared.events

import org.junit.jupiter.api.Test
import shared.events.models.Event
import shared.events.models.Subscription
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun mockedUnsubscribe(subscription: Subscription<Event>) {}

internal class SubscriptionTest_of {
    @Test fun isLookingSubscription() {
        val subscription = Subscription("test", {}, ::mockedUnsubscribe)
        assertTrue(subscription.of("test"))
    }

    @Test fun isWrongSubscription() {
        val subscription = Subscription("test", {}, ::mockedUnsubscribe)
        assertFalse(subscription.of("another_test"))
    }
}

internal class SubscriptionTest_off {
    @Test fun unsubscriptionWorks() {
        val subscription = Subscription<Event>("test", {}) {
            assertTrue(it.of("test"), "Received the same subscription object")
        }
        subscription.off()
    }
}

internal class SubscriptionTest_handle {
    private fun handleEvent(event: Event) {
        assertEquals("test", event.name)
    }

    @Test fun eventHandlingWorks() {
        val subscription = Subscription("test", this::handleEvent, ::mockedUnsubscribe)
        subscription.handle(Event("test"))
    }
}