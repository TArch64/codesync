package ua.tarch64.plugin

import ua.tarch64.shared.models.DocumentChanges

class PluginState {
    var lastDocumentChanges: DocumentChanges? = null
    var isConnectedToRoom: Boolean = false
    var config: PluginConfig = PluginConfig()
}