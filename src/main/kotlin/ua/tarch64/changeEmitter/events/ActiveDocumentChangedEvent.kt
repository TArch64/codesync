package ua.tarch64.changeEmitter.events

import ua.tarch64.shared.events.models.Event
import ua.tarch64.shared.models.DocumentChanges

data class ActiveDocumentChangedEvent(val changes: DocumentChanges): Event(ChangesEmitterEvents.ACTIVE_DOCUMENT_CHANGED.name)