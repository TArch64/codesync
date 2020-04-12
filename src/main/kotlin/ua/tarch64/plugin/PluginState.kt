package ua.tarch64.plugin

import com.intellij.openapi.editor.Document
import ua.tarch64.shared.models.TextChanges

class PluginState {
    var config: PluginConfig = PluginConfig()
    var lastTextChanges: TextChanges? = null

    var previousActiveDocument: Document? = null
    var activeDocument: Document? = null
        set(value) {
            this.previousActiveDocument = this.activeDocument
            field = value
        }
}