import { v4 as generateUuidV4 } from 'uuid';
import { ApiModule } from '../ApiModule';
import { EventContext } from '../../models';
import { CreateRoomEventPayload, JoinRoomEventPayload, Userdata } from './models';

export class ApiRooms extends ApiModule {
    private currentRoomId: string = '';
    private username: string = '';
    protected namespace: string = 'rooms';

    up() {
        this.useEvent('create', this.create);
        this.useEvent('join', this.join);
        this.useEvent('leave', this.leave);
    }

    protected down(): void {
        this.leave();
    }

    public create(context: EventContext<CreateRoomEventPayload>): void {
        this.userdata = {
            roomId: generateUuidV4(),
            username: context.payload!.username
        };
        this.socket.join(this.currentRoomId);
        this.emit({
            eventName: 'created',
            payload: { roomId: this.currentRoomId }
        });
    }

    public join(context: EventContext<JoinRoomEventPayload>): void {
        this.userdata = {
            roomId: context.payload!.roomId,
            username: context.payload!.username
        };
        this.socket.join(this.currentRoomId);
        this.emit({
            eventName: 'joined',
            payload: { roomId: this.currentRoomId }
        });
        this.emit({
            eventName: 'collaborator::joined',
            room: this.currentRoomId,
            broadcast: true,
            payload: { username: this.username }
        });
    }

    public leave(): void {
        this.emit({
            eventName: 'left',
            room: this.currentRoomId,
            broadcast: true,
            payload: { username: this.username }
        });
        this.socket.leave(this.currentRoomId);
        this.userdata = { username: '', roomId: '' };
    }

    private set userdata(data: Userdata) {
        this.currentRoomId = data.roomId;
        this.username = data.username;
    }
}
