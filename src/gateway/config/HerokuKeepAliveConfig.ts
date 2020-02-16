import { ServerConfig } from './ServerConfig';
import { TimeConfig } from './TimeConfig';

export class HerokuKeepAliveConfig {
    public endpoint = '/heroku/keep-alive';
    public requestInterval = 5 * this.time.minute;
    public absoluteEndpointUrl = this.server.appUrl + this.endpoint;

    constructor(private time: TimeConfig, private server: ServerConfig) {}

    public isKeepAliveEndpoint(path: string): boolean {
        return this.endpoint === path;
    }
}