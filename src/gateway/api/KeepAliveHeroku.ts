import { IncomingMessage, ServerResponse, RequestListener, get } from 'http';
import { get as secureGet } from 'https';
import { Config } from '../config';

export class KeepAliveHeroku {
    private readonly sendRequest: typeof get | typeof secureGet;

    constructor(private config: Config) {
        this.sendRequest = this.config.server.isSecureConnection ? secureGet : get;
    }

    public get requestHandler(): RequestListener {
        this.scheduleNextRequest();
        return (request: IncomingMessage, response: ServerResponse): void => {
            const isKeepAliveRequest = this.config.heroku.keepAlive.isKeepAliveEndpoint(request.url!);
            if ( isKeepAliveRequest ) this.scheduleNextRequest();
            response.end('Service works');
        }
    }

    private scheduleNextRequest(): void {
        const interval = this.config.heroku.keepAlive.requestInterval;
        setTimeout(() => this.makeRequest(), interval);
    }

    private makeRequest() {
        this.sendRequest(this.config.heroku.keepAlive.absoluteEndpointUrl);
    }
}
