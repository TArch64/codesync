package ua.tarch64.shared.dispatcher

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import ua.tarch64.shared.dispatcher.events.PluginDownEvent
import ua.tarch64.shared.dispatcher.events.PluginUpEvent

class Dispatcher {
    private val events = BehaviorSubject.create<Event>()
    private val stream: Observable<Event> = this.events.hide()

    val onDownStream = this.listen(PluginDownEvent.NAME)
    val onUpStream = this.listen(PluginUpEvent.NAME)

    fun trigger(event: Event) = this.events.onNext(event)

    fun listen(eventName: String): Observable<Event> {
        val onDownStream = this.getStream(PluginDownEvent.NAME)
        return this.getStream(eventName).takeUntil(onDownStream)
    }

    private fun getStream(eventName: String): Observable<Event> {
        return this.stream.filter { it.name === eventName }
    }
}