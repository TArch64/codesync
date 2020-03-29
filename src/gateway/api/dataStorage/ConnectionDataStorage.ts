import { ConnectionData } from './ConnectionData';

export class ConnectionDataStorage {
    public username: string = '';
    public currentRoomId: string = '';

    public reset() {
        this.username = '';
        this.currentRoomId = '';
    }

    public set connectionData(data: ConnectionData) {
        this.currentRoomId = data.roomId;
        this.username = data.username;
    }
}