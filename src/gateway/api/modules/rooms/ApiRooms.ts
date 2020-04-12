import { v4 as generateUuidV4 } from 'uuid';
import { ApiModule } from '../ApiModule';
import { EventContext } from '../../models';
import { CreateRoomEventPayload, JoinRoomEventPayload } from './models';

export class ApiRooms extends ApiModule {
    protected namespace: string = 'rooms';

    up() {
        this.useEvent('create', this.onCreate);
        this.useEvent('join', this.onJoin);
        this.useEvent('leave', this.onLeave);
    }

    protected down(): void {
        this.leave();
    }

    private onCreate(context: EventContext<CreateRoomEventPayload>): void {
        this.connectionDataStorage.connectionData = {
            roomId: generateUuidV4(),
            username: context.payload!.username
        };
        this.globalDataStorage.addRoomId(this.connectionDataStorage.currentRoomId);
        this.socket.join(this.connectionDataStorage.currentRoomId);
        this.emit({
            eventName: 'created',
            payload: this.roominfo,
            inCurrentRoom: false
        });
    }

    private onJoin(context: EventContext<JoinRoomEventPayload>): void {
        if (!this.validateJoinPayload(context.payload!)) return;
        this.connectionDataStorage.connectionData = {
            roomId: context.payload!.roomId,
            username: context.payload!.username
        };
        this.socket.join(this.connectionDataStorage.currentRoomId);
        this.emit({
            eventName: 'joined',
            inCurrentRoom: false,
            payload: this.roominfo
        });
        this.emit({
            eventName: 'collaborator::joined',
            broadcast: true,
            payload: this.userinfo
        });
    }

    private validateJoinPayload(payload: JoinRoomEventPayload): boolean {
        const isExistingRoomId = this.globalDataStorage.isExistingRoomId(payload.roomId);
        if (!isExistingRoomId) {
            this.reject("The room is not found");
            return false;
        }
        return true;
    }

    private onLeave(): void {
        this.emit({
            eventName: 'left',
            inCurrentRoom: false
        });
        this.leave();
    }

    private leave() {
        this.emit({
            eventName: 'collaborator::left',
            broadcast: true,
            payload: this.userinfo
        });
        this.socket.leave(this.connectionDataStorage.currentRoomId);
        this.connectionDataStorage.reset();
    }

    private get userinfo() {
        return { username: this.connectionDataStorage.username }
    }

    private get roominfo() {
        return { roomId: this.connectionDataStorage.currentRoomId };
    }
}
