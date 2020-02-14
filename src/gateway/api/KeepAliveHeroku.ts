import { IncomingMessage, ServerResponse, RequestListener, get } from 'http';
import { Config } from '../shared';

export class KeepAliveHeroku {
    constructor(private config: Config) {}

    public get requestHandler(): RequestListener {
        this.scheduleNextRequest();
        return (request: IncomingMessage, response: ServerResponse): void => {
            if ( this.config.heroku.keepAlive.isEndpoint(request.url!) ) console.log('keep alive request');
            if ( this.config.heroku.keepAlive.isEndpoint(request.url!) ) this.scheduleNextRequest();
            response.end('Service works');
        }
    }

    private scheduleNextRequest(): void {
        setTimeout(() => this.makeRequest(), this.config.heroku.keepAlive.requestInterval);
    }

    private makeRequest() {
        get(this.config.heroku.keepAlive.absoluteEndpointUrl);
    }
}
