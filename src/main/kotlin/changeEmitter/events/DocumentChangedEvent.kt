package changeEmitter.events

import shared.events.models.Event
import shared.models.DocumentChanges

data class DocumentChangedEvent(val changes: DocumentChanges): Event(ChangesEmitterEvents.DOCUMENT_CHANGED.name)