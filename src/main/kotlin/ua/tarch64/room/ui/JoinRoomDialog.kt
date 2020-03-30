package ua.tarch64.room.ui

import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.layout.CellBuilder
import com.intellij.ui.layout.Row
import com.intellij.ui.layout.panel
import ua.tarch64.shared.Regexps
import ua.tarch64.shared.ui.BaseDialog
import javax.swing.JPanel
import javax.swing.JTextField

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
                roomIdField(this).focused()
            }
        }
    }

    private fun roomIdField(parent: Row): CellBuilder<JTextField> {
        return parent.textField(this::roomId).withValidationOnApply { validateRoomIdField(it) }
    }

    private fun validateRoomIdField(roomIdField: JTextField): ValidationInfo? {
        val roomId = roomIdField.text
        return when {
            roomId.isEmpty() -> ValidationInfo("The room ID cannot be reached", roomIdField)
            !roomId.matches(Regexps.UUID) -> ValidationInfo("The ID format should be UUID", roomIdField)
            else -> null
        }
    }
}