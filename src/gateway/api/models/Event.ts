import { EventContext } from './EventContext';

export type EventHandler<Payload> = (context: EventContext<Payload>) => void;

export class Event<Payload> {
    constructor(
        public name: string,
        public handler: EventHandler<Payload>
    ) {}
}