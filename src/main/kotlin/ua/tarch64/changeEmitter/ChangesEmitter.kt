package ua.tarch64.changeEmitter

import com.intellij.openapi.editor.Document
import ua.tarch64.plugin.PluginModule
import ua.tarch64.shared.dispatcher.Event
import ua.tarch64.shared.dispatcher.events.ChangedActiveDocumentEvent

class ChangesEmitter: PluginModule() {
    private val changeDocumentHandler = this.injectModule(ChangeDocumentHandler::class.java)
    private var activeDocument: Document? = null

    override fun down() = this.disposeSubscriptions()
    override fun up() = this.keepSubscriptions(
        this.dispatcher.listen(ChangedActiveDocumentEvent.NAME).subscribe(this::changeActiveDocument)
    )

    private fun changeActiveDocument(event: Event) {
        this.removeDocumentListener()
        this.activeDocument = event.payload as Document?
        this.addDocumentListener()
    }

    private fun removeDocumentListener() {
        this.activeDocument?.removeDocumentListener(this.changeDocumentHandler)
    }

    private fun addDocumentListener() {
        this.activeDocument?.addDocumentListener(this.changeDocumentHandler)
    }
}