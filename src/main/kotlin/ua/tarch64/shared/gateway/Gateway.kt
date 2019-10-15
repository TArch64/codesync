package ua.tarch64.shared.gateway

import io.socket.client.IO
import org.json.JSONObject
import ua.tarch64.plugin.Plugin
import ua.tarch64.shared.events.models.Event
import ua.tarch64.shared.events.models.IEvents
import ua.tarch64.shared.events.models.Subscription
import ua.tarch64.shared.gateway.events.GatewayEvent

class Gateway(private val plugin: Plugin): IEvents {
    private val subscriptions: MutableList<Subscription<GatewayEvent>> = mutableListOf()
    private var socket = IO.socket(this.plugin.env.API_PATH)

    fun up() {
        this.socket = this.socket.connect()
    }

    fun down() {
        this.socket = this.socket.disconnect()
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
}