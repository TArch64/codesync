package ua.tarch64.plugin

import ua.tarch64.changeEmitter.ChangesEmitter
import ua.tarch64.documentListener.DocumentsListener
import ua.tarch64.documentUpdater.DocumentUpdater
import ua.tarch64.shared.gateway.Gateway
import ua.tarch64.shared.moduleInjection.InjectionModule

class Plugin: InjectionModule() {
    private val gateway = this.injectModule(Gateway::class.java)
    private val documentListener = this.injectModule(DocumentsListener::class.java)
    private val changesEmitter = this.injectModule(ChangesEmitter::class.java)
    private val documentUpdater = this.injectModule(DocumentUpdater::class.java)

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

    private fun removeEventListeners() {}
}