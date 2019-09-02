package changeEmitter.events

import shared.events.models.Event
import shared.models.DocumentChanges

data class ActiveDocumentChangedEvent(val changes: DocumentChanges): Event(ChangesEmitterEvents.ACTIVE_DOCUMENT_CHANGED.name)