package shared.events

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

fun mockedUnsubscribe(subscription: Subscription) {}

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
        val subscription = Subscription("test", {}) {
            assertTrue(it.of("test"), "Received the same subscription object")
        }
        subscription.off()
    }
}

internal class SubscriptionTest_handle {
    private fun handleEvent(payload: Any?) {
        assertEquals("Subscription works!", payload)
    }

    @Test fun eventHandlingWorks() {
        val subscription = Subscription("test", this::handleEvent, ::mockedUnsubscribe)
        subscription.handle("Subscription works!")
    }
}