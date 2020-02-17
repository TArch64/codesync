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

    public create(context: EventContext<void>): void {
        this.currentRoomId = this.generateUniqRoomId(context.socket.client.id);
        context.socket.emit('created', {
            roomId: this.currentRoomId
        });
    }

    private generateUniqRoomId(clientId: string): string {
        const payload = { clientId, createdAt: Date.now() };
        return btoa(JSON.stringify(payload));
    }

    public connect(context: EventContext<RoomEventPayload>): void {
        this.currentRoomId = context.payload!.roomId;
        context.socket.join(this.currentRoomId);
        context.socket.in(this.currentRoomId).broadcast.emit('connected', {
            username: context.payload!.username
        })
    }

    public leave(context: EventContext<RoomEventPayload>): void {
        context.socket.in(this.currentRoomId).broadcast.emit('disconnected', {
            username: context.payload!.username
        });
        context.socket.leave(this.currentRoomId);
        this.currentRoomId = '';
    }
}
