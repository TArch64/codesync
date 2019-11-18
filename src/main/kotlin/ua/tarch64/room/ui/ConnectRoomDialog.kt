package ua.tarch64.room.ui

import com.intellij.ui.layout.panel
import ua.tarch64.shared.ui.BaseDialog
import javax.swing.JPanel

class ConnectRoomDialog: BaseDialog<ConnectRoomDialog>() {
    var serviceUrl: String = ""

    override fun render(): JPanel {
        this.title = "Gateway Service URL"
        this.setResizable(false)
        this.setSize(300, 0)

        return panel {
            row {
                label("Run 'Gateway Service' for your OS and copy&past\nservice's public URL into following field")
            }
            row {
                textField(::serviceUrlGetter, ::serviceUrlSetter).focused()
            }
        }
    }

    private fun serviceUrlGetter(): String = this.serviceUrl
    private fun serviceUrlSetter(url: String) { this.serviceUrl = url }
}