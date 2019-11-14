package ua.tarch64.room.ui

import com.intellij.ui.layout.panel
import ua.tarch64.shared.ui.BaseDialog
import javax.swing.JPanel

class ConnectRoomDialog: BaseDialog() {
    private var serviceUrl: String = ""

    override fun render(): JPanel {
        this.title = "Gateway Service URL"

        return panel {
            row {
                textField(::serviceUrlGetter, ::serviceUrlSetter).focused()
            }
        }
    }

    fun serviceUrlGetter(): String = this.serviceUrl
    private fun serviceUrlSetter(url: String) { this.serviceUrl = url }
}