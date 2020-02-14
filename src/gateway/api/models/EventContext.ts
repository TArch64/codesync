import { Socket } from 'socket.io';

export class EventContext<Payload> {
    constructor(
        public socket: Socket,
        public payload?: Payload
    ) {}
}