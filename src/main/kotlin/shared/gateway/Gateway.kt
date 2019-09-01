package shared.gateway

import io.socket.client.IO
import io.socket.client.Socket
import shared.Environment
import shared.events.models.Event
import shared.events.models.IEvents
import shared.events.models.Subscription
import shared.gateway.events.GatewayEvent
import shared.models.Jsonable

class Gateway(private val apiPath: String): IEvents {
    private val subscriptions: MutableList<Subscription<GatewayEvent>> = mutableListOf()
    private val socket: Socket = initializeSocket().connect()

    private fun initializeSocket(): Socket {
        return IO.socket(this.apiPath)
    }

    override fun <T : Event> on(eventName: String, handler: (event: T) -> Unit): Subscription<out Event> {
        val subscription = Subscription(eventName, handler as (event: GatewayEvent) -> Unit, this::off)
        this.socket.on(eventName) { subscription.handle(GatewayEvent(eventName, Jsonable(it))) }
        this.subscriptions.add(subscription)
        return subscription
    }

    private fun off(subscription: Subscription<GatewayEvent>) {}

    override fun trigger(event: Event) {}

    companion object {
        lateinit var instance: Gateway

        fun initialize() {
            this.instance = Gateway(Environment.API_PATH)
        }
    }
}