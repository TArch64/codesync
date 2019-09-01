package plugin

import com.intellij.openapi.components.ApplicationComponent
import shared.changeEmitter.ChangesEmitter
import shared.documentListener.DocumentsListener
import shared.ui.Notifications

class PluginComponent: ApplicationComponent {
    private val documentListener = DocumentsListener()
    private val changeListener = ChangesEmitter()

    override fun initComponent() {
        super.initComponent()

        documentListener.onActiveDocumentChange { changeListener.changeActiveDocument(it.document) }
        changeListener.onDocumentChanged { Notifications.notifyInfo(it.changes) }
    }
}