package changeEmitter

import com.intellij.openapi.editor.Document
import changeEmitter.events.ChangesEmitterEvents
import changeEmitter.events.ActiveDocumentChangedEvent
import changeEmitter.events.DocumentChangedEvent
import shared.events.Events
import shared.gateway.Gateway
import shared.gateway.events.GatewayEvent
import shared.gateway.events.GatewayEvents
import shared.models.DocumentChanges

class ChangesEmitter: Events() {
    private var activeDocument: Document? = null
    private val changeDocumentHandler = ChangeDocumentHandler(this)

    fun setup() {
        Gateway.instance.on(GatewayEvents.DOCUMENT_CHANGED.name, this::onReceivedExternalChanges)

        this.on(ChangesEmitterEvents.ACTIVE_DOCUMENT_CHANGED.name) { event: ActiveDocumentChangedEvent ->
            val gatewayEvent = GatewayEvent(GatewayEvents.SEND_DOCUMENT_CHANGES.name, event.changes.toJSON())
            Gateway.instance.trigger(gatewayEvent)
        }
    }

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