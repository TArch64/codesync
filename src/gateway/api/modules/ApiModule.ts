import { Event, EventContext, EventHandler } from '../models';

export abstract class ApiModule {
    private events = new Map<string, EventHandler<any>>();

    constructor(private namespace: string) {
        this.up();
    }

    abstract up(): void;

    protected useEvent<Payload = object>(eventName: string, handler: EventHandler<Payload>): void {
        if ( this.events.has(eventName) ) throw `Duplicated event handler for "${eventName}"`;

        this.events.set(eventName, (context: EventContext<Payload>) => handler(context));
    }

    get namespacedEventsList(): Event<object>[] {
        const eventsList: Event<object>[] = [];
        this.events.forEach((handler: EventHandler<object>, name) => {
            const namespacedName = `${this.namespace}::${name}`;
            eventsList.push({ name: namespacedName, handler });
        });
        return eventsList;
    }
}
