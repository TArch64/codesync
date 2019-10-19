package ua.tarch64.plugin

import io.reactivex.disposables.Disposable
import ua.tarch64.shared.dispatcher.Dispatcher
import ua.tarch64.shared.dispatcher.events.PluginDownEvent
import ua.tarch64.shared.dispatcher.events.PluginUpEvent
import ua.tarch64.shared.moduleInjection.IInjectionModule
import ua.tarch64.shared.moduleInjection.InjectionModule

abstract class PluginModule: IInjectionModule by InjectionModule() {
    protected val dispatcher = this.injectModule(Dispatcher::class.java)
    protected var subscriptions: List<Disposable>? = null

    init {
        this.dispatcher.listen(PluginUpEvent.NAME).subscribe { this.up() }
        this.dispatcher.listen(PluginDownEvent.NAME).subscribe { this.down() }
    }

    protected abstract fun up()
    protected abstract fun down()

    protected fun keepSubscriptions(vararg subscriptions: Disposable) {
        this.subscriptions = subscriptions.asList()
    }

    protected fun disposeSubscriptions() {
        this.subscriptions?.forEach { it.dispose() }
        this.subscriptions = null
    }
}