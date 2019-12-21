package ua.tarch64.shared

import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import ua.tarch64.plugin.PluginModule
import ua.tarch64.plugin.PluginState
import ua.tarch64.shared.dispatcher.Event as DispatcherEvent
import ua.tarch64.shared.dispatcher.events.SendDocumentChangesEvent
import ua.tarch64.shared.dispatcher.events.UpdateDocumentEvent
import ua.tarch64.shared.models.DocumentChanges

class Gateway: PluginModule() {
    private val pluginState: PluginState = ModuleInjector.inject()
    private lateinit var socket: Socket

    override fun up() {
        this.socket = IO.socket(this.pluginState.config.gatewayServiceUrl).connect()
        this.socket.on(UpdateDocumentEvent.NAME, this::onReceivedExternalChanges)

        this.keepSubscriptions(
            this.dispatcher.listen(SendDocumentChangesEvent.NAME).subscribe(this::sendDocumentChanges)
        )
    }

    private fun sendDocumentChanges(event: DispatcherEvent) {
        val json = (event.payload as DocumentChanges).toJSON()
        this.socket.emit(event.name, json)
    }

    private fun onReceivedExternalChanges(vararg args: Any) {
        val documentChanges = DocumentChanges.fromJSON(args.first() as JSONObject)
        val event = UpdateDocumentEvent(documentChanges)
        this.dispatcher.trigger(event)
    }

    override fun down() {
        this.disposeSubscriptions()
        this.socket.off(UpdateDocumentEvent.NAME, this::onReceivedExternalChanges)
        this.socket = this.socket.disconnect()
    }
}