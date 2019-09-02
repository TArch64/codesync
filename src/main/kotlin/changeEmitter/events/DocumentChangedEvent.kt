package changeEmitter.events

import shared.events.models.Event

data class DocumentChangedEvent(val changes: String): Event(ChangesEmitterEvents.DOCUMENT_CHANGED.name)