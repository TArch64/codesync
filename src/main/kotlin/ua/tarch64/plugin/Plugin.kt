package ua.tarch64.plugin

import ua.tarch64.changeEmitter.ChangesEmitter
import ua.tarch64.documentListener.DocumentsListener
import ua.tarch64.documentUpdater.DocumentUpdater
import ua.tarch64.shared.gateway.Gateway
import ua.tarch64.shared.ui.Toasts

class Plugin {
    val env = PluginEnv()
    val state = PluginState()
    val gateway = Gateway(this)
    val toasts = Toasts()

    private val documentListener = DocumentsListener()
    private val changesEmitter = ChangesEmitter(this)
    private val documentUpdater = DocumentUpdater(this)

    fun up() {
        this.gateway.up()
        this.documentListener.up()
        this.changesEmitter.up()
        this.listenEvents()
    }

    private fun listenEvents() {
        this.documentListener.onActiveDocumentChange { this.changesEmitter.changeActiveDocument(it.document) }
        this.changesEmitter.onDocumentChanged { this.documentUpdater.updateDocument(it.changes) }
    }

    fun down() {
        this.changesEmitter.down()
        this.documentListener.down()
        this.gateway.down()
        this.removeEventListeners()
    }

    private fun removeEventListeners() {

    }

    companion object { val instance = Plugin() }
}