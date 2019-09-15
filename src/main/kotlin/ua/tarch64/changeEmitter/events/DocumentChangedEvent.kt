package ua.tarch64.changeEmitter.events

import ua.tarch64.shared.events.models.Event
import ua.tarch64.shared.models.DocumentChanges

data class DocumentChangedEvent(val changes: DocumentChanges): Event(ChangesEmitterEvents.DOCUMENT_CHANGED.name)