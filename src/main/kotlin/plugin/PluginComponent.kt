package plugin

import com.intellij.openapi.components.ApplicationComponent
import changeEmitter.ChangesEmitter
import documentListener.DocumentsListener
import shared.gateway.Gateway
import shared.gateway.events.GatewayEvent
import shared.ui.Notifications

class PluginComponent: ApplicationComponent {
    private val documentListener = DocumentsListener()
    private val changesEmitter = ChangesEmitter()

    override fun initComponent() {
        super.initComponent()

        Notifications.setup()
        Gateway.setup()

        this.documentListener.setup()
        this.changesEmitter.setup()

        this.documentListener.onActiveDocumentChange { this.changesEmitter.changeActiveDocument(it.document) }
        this.changesEmitter.onDocumentChanged { Notifications.notifyInfo(it.changes) }
    }
}