package plugin

import com.intellij.openapi.components.ApplicationComponent
import changeEmitter.ChangesEmitter
import documentListener.DocumentsListener
import shared.gateway.Gateway
import shared.ui.Notifications

class PluginComponent: ApplicationComponent {
    private val documentListener = DocumentsListener()
    private val changesEmitter = ChangesEmitter()

    override fun initComponent() {
        super.initComponent()
        this.setup()
        this.listenEvents()
    }

    private fun setup() {
        Notifications.setup()
        Gateway.setup()

        this.documentListener.setup()
        this.changesEmitter.setup()
    }

    private fun listenEvents() {
        this.documentListener.onActiveDocumentChange { this.changesEmitter.changeActiveDocument(it.document) }
        this.changesEmitter.onActiveDocumentChanged { Notifications.notifyInfo(it.changes.changed) }
        this.changesEmitter.onDocumentChanged { Notifications.notifyInfo(it.changes.changed) }
    }
}