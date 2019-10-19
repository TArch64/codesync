package ua.tarch64.shared.dispatcher

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class Dispatcher {
    private val events = BehaviorSubject.create<Event>()
    private val stream: Observable<Event> = this.events.hide()

    fun trigger(event: Event) = this.events.onNext(event)

    fun listen(eventName: String): Observable<Event> {
        return this.stream.filter { it.name === eventName }
    }
}