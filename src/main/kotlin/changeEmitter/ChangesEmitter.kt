package changeEmitter

import com.intellij.openapi.editor.Document
import changeEmitter.events.ChangesEmitterEvents
import changeEmitter.events.DocumentChangedEvent
import shared.events.Events
import shared.gateway.Gateway
import shared.gateway.events.GatewayEvent
import shared.ui.Notifications

class ChangesEmitter: Events() {
    private var activeDocument: Document? = null
    private val changeDocumentHandler = ChangeDocumentHandler(this)

    fun setup() {
        Gateway.instance.on<GatewayEvent>("test") { Notifications.notifyInfo(it.toString()) }
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