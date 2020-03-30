export class GlobalDataStorage {
    private roomIds: string[] = [];

    public addRoomId(roomId: string): void {
        this.roomIds.push(roomId);
    }

    public isExistingRoomId(roomId: string): boolean {
        return this.roomIds.includes(roomId);
    }
}