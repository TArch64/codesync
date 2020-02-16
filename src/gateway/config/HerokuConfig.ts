import { HerokuKeepAliveConfig } from './HerokuKeepAliveConfig';
import { TimeConfig } from './TimeConfig';
import { ServerConfig } from './ServerConfig';

export class HerokuConfig {
    public keepAlive = new HerokuKeepAliveConfig(this.time, this.server);

    constructor(private time: TimeConfig, private server: ServerConfig) {}
}