import { Event, EventContext, EventHandler } from '../models';

export abstract class ApiModule {
    private readonly events = new Map<string, EventHandler<any>>();
    protected abstract namespace: string;

    constructor() {
        this.up();
    }

    protected abstract up(): void;

    protected useEvent<Payload = object>(eventName: string, handler: EventHandler<Payload>): void {
        if ( this.events.has(eventName) ) throw `Duplicated event handler for "${eventName}"`;

        this.events.set(eventName, (context: EventContext<Payload>) => handler(context));
    }

    public get namespacedEventsList(): Event<object>[] {
        const eventsList: Event<object>[] = [];
        this.events.forEach((handler: EventHandler<object>, name) => {
            const namespacedName = `${this.namespace}::${name}`;
            eventsList.push({ name: namespacedName, handler });
        });
        return eventsList;
    }
}
