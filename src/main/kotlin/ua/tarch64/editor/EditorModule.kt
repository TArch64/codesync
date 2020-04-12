package ua.tarch64.editor

import ua.tarch64.editor.text.InputTextModule
import ua.tarch64.editor.text.OutputTextModule
import ua.tarch64.plugin.PluginModule
import ua.tarch64.shared.ModuleInjector

class EditorModule: PluginModule() {
    init {
        ModuleInjector.inject<ActiveDocumentModule>()
        ModuleInjector.inject<OutputTextModule>()
        ModuleInjector.inject<InputTextModule>()
    }
}