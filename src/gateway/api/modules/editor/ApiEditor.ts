import { ApiModule } from '../ApiModule';
import { EventContext } from '../../models';

export class ApiEditor extends ApiModule {
    protected namespace: string = 'editor';

    protected up(): void {
        this.useEvent('changed', this.onChanged)
    }

    private onChanged(context: EventContext<any>) {
        this.emit({
            eventName: 'sync',
            broadcast: true,
            payload: context.payload
        });
    }
}