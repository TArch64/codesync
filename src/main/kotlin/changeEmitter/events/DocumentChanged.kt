package changeEmitter.events

import shared.events.models.Event

data class DocumentChanged(val changes: String): Event(ChangeListenerEvents.DOCUMENT_CHANGED.name)