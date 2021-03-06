export class Logger {
    public exec(name: string, data: any, isRequestEvent: boolean): void {
        console.log('|\n'.repeat(2));
        this.logHeaders(isRequestEvent);
        console.log(`api_event: ${name}`);
        if (data) this.log('event_payload', data);
    }

    private logHeaders(isRequestEvent: boolean) {
        console.log(new Date().toUTCString());
        const eventType = isRequestEvent ? 'request' : 'response';
        console.log(`event_type: ${eventType}`);
    }

    private log(name: string, data: any): void {
        if ( Array.isArray(data) ) {
            this.logArray(name, data);
            return;
        }

        if ( typeof data === 'object') {
            this.logObject(name, data);
            return;
        }

        this.logDefault(name, data);
    }

    private logArray(name: string, data: any[]): void {
        this.logGroup(name, () => {
            data.forEach((dataItem: any, index: number) => this.log(index.toString(), dataItem));
        });
    }

    private logGroup(name: string, logData: () => void): void {
        console.group(name);
        logData();
        console.groupEnd();
    }

    private logObject(name: string, data: object): void {
        this.logGroup(name, () => {
            Object.entries(data).forEach(([key, value]: [string, any]) => this.log(key, value));
        })
    }

    private logDefault(name: string, data: any): void {
        console.log(`${name}: ${data}`);
    }
}