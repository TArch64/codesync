package ua.tarch64.room.ui

import com.intellij.ui.layout.panel
import ua.tarch64.shared.ui.BaseDialog
import javax.swing.JPanel

class JoinRoomDialog: BaseDialog<JoinRoomDialog>() {
    var roomId: String = ""

    override fun render(): JPanel {
        this.title = "Join to Room"
        this.setResizable(false)
        this.setSize(300, 0)

        return panel {
            row {
                label("Enter room ID into following field")
            }
            row {
                textField(::roomIdGetter, ::roomIdSetter).focused()
            }
        }
    }

    private fun roomIdGetter(): String = this.roomId
    private fun roomIdSetter(roomId: String) { this.roomId = roomId }
}