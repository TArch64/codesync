package plugin

import com.intellij.openapi.components.ApplicationComponent
import changeEmitter.ChangesEmitter
import documentListener.DocumentsListener
import shared.gateway.Gateway
import shared.gateway.events.GatewayEvent
import shared.ui.Notifications

class PluginComponent: ApplicationComponent {
    private val documentListener = DocumentsListener()
    private val changeListener = ChangesEmitter()

    override fun initComponent() {
        super.initComponent()

        Gateway.initialize()

        documentListener.onActiveDocumentChange { changeListener.changeActiveDocument(it.document) }
        changeListener.onDocumentChanged { Notifications.notifyInfo(it.changes) }

        Gateway.instance.on<GatewayEvent>("test") { Notifications.notifyInfo(it.toString()) }
    }
}