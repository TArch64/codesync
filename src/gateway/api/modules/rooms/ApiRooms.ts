import { v4 as generateUuidV4 } from 'uuid';
import { ApiModule } from '../ApiModule';
import { EventContext } from '../../models';
import { RoomEventPayload } from './models/RoomEventPayload';

export class ApiRooms extends ApiModule {
    private currentRoomId: string = '';
    protected namespace: string = 'rooms';

    up() {
        this.useEvent('create', this.create);
        this.useEvent('connect', this.connect);
        this.useEvent('leave', this.leave);
    }

    public create(): void {
        this.currentRoomId = generateUuidV4();
        this.emit({
            eventName: 'created',
            useCurrentNamespace: true,
            payload: { roomId: this.currentRoomId }
        });
    }

    public connect(context: EventContext<RoomEventPayload>): void {
        this.currentRoomId = context.payload!.roomId;
        context.socket.join(this.currentRoomId);
        this.emit({
            eventName:'connected',
            useCurrentNamespace: true,
            room: this.currentRoomId,
            broadcast: true,
            payload: { username: context.payload!.username }
        });
    }

    public leave(context: EventContext<RoomEventPayload>): void {
        this.emit({
            eventName: 'disconnected',
            useCurrentNamespace: true,
            room: this.currentRoomId,
            broadcast: true,
            payload: { username: context.payload!.username }
        });
        context.socket.leave(this.currentRoomId);
        this.currentRoomId = '';
    }
}
