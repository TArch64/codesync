package ua.tarch64.shared.gateway

import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import ua.tarch64.shared.Environment
import ua.tarch64.shared.events.models.Event
import ua.tarch64.shared.events.models.IEvents
import ua.tarch64.shared.events.models.Subscription
import ua.tarch64.shared.gateway.events.GatewayEvent

class Gateway(private val apiPath: String): IEvents {
    private val subscriptions: MutableList<Subscription<GatewayEvent>> = mutableListOf()
    private val socket: Socket = initializeSocket().connect()

    private fun initializeSocket(): Socket {
        return IO.socket(this.apiPath)
    }

    override fun <T : Event> on(eventName: String, handler: (event: T) -> Unit): Subscription<out Event> {
        val subscription = Subscription(eventName, handler as (event: GatewayEvent) -> Unit)
        this.socket.on(eventName) { subscription.handle(GatewayEvent(eventName, it.first() as JSONObject)) }
        this.subscriptions.add(subscription)
        return subscription
    }

    override fun trigger(event: Event) {
        val gatewayEvent = event as GatewayEvent
        this.socket.emit(gatewayEvent.name, gatewayEvent.payload)
    }

    companion object {
        lateinit var instance: Gateway

        fun setup() {
            this.instance = Gateway(Environment.API_PATH)
        }
    }
}