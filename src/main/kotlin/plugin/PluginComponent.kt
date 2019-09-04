package plugin

import com.intellij.openapi.components.ApplicationComponent
import changeEmitter.ChangesEmitter
import documentListener.DocumentsListener
import documentUpdater.DocumentUpdater
import shared.gateway.Gateway
import shared.ui.Notifications

class PluginComponent: ApplicationComponent {
    private val documentListener = DocumentsListener()
    private val changesEmitter = ChangesEmitter()
    private val documentUpdater = DocumentUpdater()

    override fun initComponent() {
        super.initComponent()
        try {
            this.setup()
            this.listenEvents()
        } catch (exception: Exception) {
            Notifications.notifyError(exception.message ?: "Something went wrong")
        }
    }

    private fun setup() {
        Notifications.setup()
        Gateway.setup()

        this.documentListener.setup()
        this.changesEmitter.setup()
    }

    private fun listenEvents() {
        this.documentListener.onActiveDocumentChange { this.changesEmitter.changeActiveDocument(it.document) }
        this.changesEmitter.onActiveDocumentChanged {
            Notifications.notifyInfo("${it.changes.documentRelativePath}: ${it.changes.changed}")
        }
        this.changesEmitter.onDocumentChanged { this.documentUpdater.updateDocument(it.changes) }
    }
}