package plugin

import com.intellij.openapi.components.ApplicationComponent
import shared.documentListener.DocumentsListener
import shared.ui.Notifications

class PluginComponent: ApplicationComponent {
    override fun initComponent() {
        super.initComponent()
        DocumentsListener.onActiveDocumentChange {
            Notifications.notifyInfo(it.newDocument!!.text)
        }
    }
}