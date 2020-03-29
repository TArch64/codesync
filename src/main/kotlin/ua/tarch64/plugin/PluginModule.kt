package ua.tarch64.plugin

import ua.tarch64.dispatcher.Dispatcher
import ua.tarch64.shared.ModuleInjector

abstract class PluginModule {
    protected val dispatcher: Dispatcher = ModuleInjector.inject()

    init {
        this.dispatcher.onUpStream.subscribe { this.up() }
        this.dispatcher.onDownStream.subscribe { this.down() }
    }

    protected open fun up() = Unit
    protected open fun down() = Unit
}