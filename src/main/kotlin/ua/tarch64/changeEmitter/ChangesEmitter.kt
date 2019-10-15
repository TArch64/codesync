package ua.tarch64.changeEmitter

import com.intellij.openapi.editor.Document
import ua.tarch64.changeEmitter.events.ChangesEmitterEvents
import ua.tarch64.changeEmitter.events.ActiveDocumentChangedEvent
import ua.tarch64.changeEmitter.events.DocumentChangedEvent
import ua.tarch64.plugin.Plugin
import ua.tarch64.shared.events.Events
import ua.tarch64.shared.gateway.events.GatewayEvent
import ua.tarch64.shared.gateway.events.GatewayEvents
import ua.tarch64.shared.models.DocumentChanges

class ChangesEmitter(private val plugin: Plugin): Events() {
    private var activeDocument: Document? = null
    private val changeDocumentHandler = ChangeDocumentHandler(this.plugin, this)

    fun up() {
        this.plugin.gateway.on(GatewayEvents.DOCUMENT_CHANGED.name, this::onReceivedExternalChanges)

        this.on(ChangesEmitterEvents.ACTIVE_DOCUMENT_CHANGED.name) { event: ActiveDocumentChangedEvent ->
            val gatewayEvent = GatewayEvent(GatewayEvents.SEND_DOCUMENT_CHANGES.name, event.changes.toJSON())
            this.plugin.gateway.trigger(gatewayEvent)
        }
    }

    fun down() {}

    private fun onReceivedExternalChanges(event: GatewayEvent) {
        val documentChangedEvent = DocumentChangedEvent(
            DocumentChanges.fromJSON(event.payload!!)
        )
        this.trigger(documentChangedEvent)
    }

    fun changeActiveDocument(document: Document?) {
        this.removeDocumentListener()
        this.activeDocument = document
        this.addDocumentListener()
    }

    private fun removeDocumentListener() {
        this.activeDocument?.removeDocumentListener(this.changeDocumentHandler)
    }

    private fun addDocumentListener() {
        this.activeDocument?.addDocumentListener(this.changeDocumentHandler)
    }

    fun onDocumentChanged(handler: (event: DocumentChangedEvent) -> Unit) {
        this.on(ChangesEmitterEvents.DOCUMENT_CHANGED.name, handler)
    }
}