import { HerokuConfig } from './HerokuConfig';
import { TimeConfig } from './TimeConfig';
import { ServerConfig } from './ServerConfig';

export class Config {
    public time = new TimeConfig();
    public server = new ServerConfig();
    public heroku = new HerokuConfig(this.time, this.server);
}
