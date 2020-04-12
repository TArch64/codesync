import useSocket, { Socket } from 'socket.io';
import { createServer } from 'http';
import { Config } from '../config';
import { ApiModule, ApiRooms } from './modules';
import { Event, EventContext } from './models';
import { KeepAliveHeroku } from './KeepAliveHeroku';
import { Logger } from '../Logger';
import { ConnectionDataStorage, GlobalDataStorage } from './dataStorage';

export class ApiRoot {
    private globalDataStorage = new GlobalDataStorage();
    private modules = [
        ApiRooms
    ];

    constructor(private config: Config, private logger: Logger) {}

    public up(): Promise<void> {
        const keepAliveHeroku = new KeepAliveHeroku(this.config);
        const server = createServer(keepAliveHeroku.requestHandler);
        const io = useSocket(server);

        io.on('connection', this.handleSocketConnection.bind(this));

        return new Promise(resolve => server.listen(this.config.server.port, resolve));
    }

    private handleSocketConnection(socket: Socket): void {
        this.createApiEvents(socket).forEach((event: Event<any>) => this.listenEvent(socket, event))
    }

    private createApiEvents(socket: Socket): Event<any>[] {
        const connectionDataStorage = new ConnectionDataStorage();
        const createModule = (ModuleClass: any): ApiModule => {
            return new ModuleClass(
                socket,
                connectionDataStorage,
                this.globalDataStorage,
                this.logger
            );
        };
        return this.modules
            .map(module => createModule(module).namespacedEventsList)
            .reduce((list, events) => list.concat(events), []);
    }

    private listenEvent(socket: Socket, event: Event<any>): void {
        socket.on(event.name, (payload: any) => {
            event.handler(new EventContext<any>(socket, payload));
        })
    }
}
