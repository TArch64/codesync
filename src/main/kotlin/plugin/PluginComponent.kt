package plugin

import com.intellij.openapi.components.ApplicationComponent
import changeEmitter.ChangesEmitter
import documentListener.DocumentsListener
import documentUpdater.DocumentUpdater
import shared.gateway.Gateway
import shared.ui.Toasts

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
            Toasts.notifyError(exception.message ?: "Something went wrong")
        }
    }

    private fun setup() {
        Toasts.setup()
        Gateway.setup()

        this.documentListener.setup()
        this.changesEmitter.setup()
    }

    private fun listenEvents() {
        this.documentListener.onActiveDocumentChange { this.changesEmitter.changeActiveDocument(it.document) }
        this.changesEmitter.onDocumentChanged { this.documentUpdater.updateDocument(it.changes) }
    }
}