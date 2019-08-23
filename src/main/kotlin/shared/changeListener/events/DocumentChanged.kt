package shared.changeListener.events

import shared.events.Event

data class DocumentChanged(val changes: String): Event(ChangeListenerEvents.DOCUMENT_CHANGED.name)