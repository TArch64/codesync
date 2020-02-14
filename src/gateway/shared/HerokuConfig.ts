import { Config } from './Config';
import { HerokuKeepAliveConfig } from './HerokuKeepAliveConfig';

export class HerokuConfig {
    public keepAlive = new HerokuKeepAliveConfig(this.root);

    constructor(private root: Config) {}
}