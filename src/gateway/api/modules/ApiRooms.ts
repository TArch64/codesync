import { ApiModule } from './ApiModule';

export class ApiRooms extends ApiModule {
    up() {
        this.useEvent('create', this.create);
        this.useEvent('connect', this.connect);
        this.useEvent('leave', this.leave);
    }

    create() {}
    connect() {}
    leave() {}
}
