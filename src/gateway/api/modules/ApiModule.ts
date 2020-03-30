import { Event, EventContext, EventHandler } from '../models';
import { Logger } from '../../Logger';
import { Socket } from 'socket.io';
import { IEmitResponseOptions } from '../models/IEmitResponseOptions';
import {ConnectionDataStorage, GlobalDataStorage} from '../dataStorage';

export abstract class ApiModule {
    private readonly events = new Map<string, EventHandler<any>>();
    protected abstract namespace: string;

    constructor(
        protected socket: Socket,
        protected connectionDataStorage: ConnectionDataStorage,
        protected globalDataStorage: GlobalDataStorage,
        private logger: Logger
    ) {
        this.up();
        this.socket.on('disconnect', this.down.bind(this));
    }

    protected abstract up(): void;
    protected down(): void {};

    protected useEvent<Payload = object>(eventName: string, handler: EventHandler<Payload>): void {
        if ( this.events.has(eventName) ) throw `Duplicated event handler for "${eventName}"`;

        this.events.set(eventName, (context: EventContext<Payload>) => {
            this.logger.exec(this.makeNamespacedEventName(eventName), context.payload, true);
            handler.call(this,context);
        });
    }

    private makeNamespacedEventName(name: string): string {
        return `${this.namespace}::${name}`;
    }

    protected emit(options: IEmitResponseOptions): void {
        options = this.prepareEmitOptions(options);
        let socket: Socket = this.socket;
        if (options.inCurrentRoom) socket = socket.in(this.connectionDataStorage.currentRoomId);
        if (options.broadcast) socket = socket.broadcast;
        if (options.useCurrentNamespace) options.eventName = this.makeNamespacedEventName(options.eventName);
        socket.emit(options.eventName, options.payload);
        this.logger.exec(options.eventName, options.payload, false);
    }

    private prepareEmitOptions(options: IEmitResponseOptions): IEmitResponseOptions {
        return {
            useCurrentNamespace: true,
            broadcast: false,
            inCurrentRoom: true,
            ...options
        };
    }

    protected reject(message: string): void {
        this.emit({
            eventName: 'common::error',
            inCurrentRoom: false,
            useCurrentNamespace: false,
            payload: { description: message }
        })
    }

    public get namespacedEventsList(): Event<object>[] {
        const eventsList: Event<object>[] = [];
        this.events.forEach((handler: EventHandler<object>, name) => {
            eventsList.push({ name: this.makeNamespacedEventName(name), handler });
        });
        return eventsList;
    }
}
