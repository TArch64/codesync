package plugin

import changeEmitter.ChangesEmitter
import documentListener.DocumentsListener
import documentUpdater.DocumentUpdater
import shared.gateway.Gateway
import shared.ui.Toasts

class Plugin {
    public val state = PluginState()

    private val documentListener = DocumentsListener()
    private val changesEmitter = ChangesEmitter()
    private val documentUpdater = DocumentUpdater()

    init {
        Toasts.setup()
        Gateway.setup()

        this.documentListener.setup()
        this.changesEmitter.setup()
    }

    fun up() {
        this.listenEvents()
    }

    private fun listenEvents() {
        this.documentListener.onActiveDocumentChange { this.changesEmitter.changeActiveDocument(it.document) }
        this.changesEmitter.onDocumentChanged { this.documentUpdater.updateDocument(it.changes) }
    }

    companion object { val instance = Plugin() }
}