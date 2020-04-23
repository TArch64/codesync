import { ApiModule } from '../ApiModule';
import { EventContext } from '../../models';

export class ApiFileSystem extends ApiModule {
    protected namespace: string = "file:system";

    protected up(): void {
        this.useEvent('create', this.onCreate);
        this.useEvent('delete', this.onDelete);
        this.useEvent('move', this.onMove);
        this.useEvent('rename', this.onRename);
    }

    private onCreate(context: EventContext<any>): void {
        this.emit({
            eventName: 'created',
            broadcast: true,
            payload: context.payload
        })
    }

    private onDelete(context: EventContext<any>): void {
        this.emit({
            eventName: 'deleted',
            broadcast: true,
            payload: context.payload
        })
    }

    private onMove(context: EventContext<any>): void {
        this.emit({
            eventName: 'moved',
            broadcast: true,
            payload: context.payload
        })
    }

    private onRename(context: EventContext<any>): void {
        this.emit({
            eventName: 'renamed',
            broadcast: true,
            payload: context.payload
        })
    }
}