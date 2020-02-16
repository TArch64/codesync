import { Config } from './Config';

export class HerokuKeepAliveConfig {
    public endpoint = '/heroku/keep-alive';
    public requestInterval = 5 * this.root.time.minute;
    public absoluteEndpointUrl = this.root.appUrl + this.endpoint;

    constructor(private root: Config) {}

    public isKeepAliveEndpoint(path: string): boolean {
        return this.endpoint === path;
    }
}