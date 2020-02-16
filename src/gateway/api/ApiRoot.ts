import * as useSocket from 'socket.io';
import { createServer } from 'http';
import { Config } from '../shared';
import { ApiRooms, ApiModule } from './modules';
import { Event, EventContext } from './models';
import { KeepAliveHeroku } from './KeepAliveHeroku';

export class ApiRoot {
    constructor(private config: Config) {}

    public up(): Promise<void> {
        const keepAliveHeroku = new KeepAliveHeroku(this.config);
        const server = createServer(keepAliveHeroku.requestHandler);
        const io = useSocket(server);

        io.on('connection', this.handleSocketConnection.bind(this));

        return new Promise(resolve => server.listen(this.config.port, resolve));
    }

    private handleSocketConnection(socket: useSocket.Socket): void {
        this.createApiEvents().forEach((event: Event<any>) => this.listenEvent(socket, event))
    }

    private createApiEvents(): Event<any>[] {
        const modules: ApiModule[] = [
            new ApiRooms('rooms')
        ];

        return modules.flatMap((module: ApiModule) => module.namespacedEventsList);
    }

    private listenEvent(socket: useSocket.Socket, event: Event<any>): void {
        socket.on(event.name, (payload: any) => {
            event.handler(new EventContext<any>(socket, payload));
        })
    }
}