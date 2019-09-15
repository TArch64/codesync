package ua.tarch64.plugin

import ua.tarch64.changeEmitter.ChangesEmitter
import ua.tarch64.documentListener.DocumentsListener
import ua.tarch64.documentUpdater.DocumentUpdater
import ua.tarch64.shared.gateway.Gateway
import ua.tarch64.shared.ui.Toasts

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