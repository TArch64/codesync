import { HerokuConfig } from './HerokuConfig';
import { TimeConfig } from './TimeConfig';

export class Config {
    public defaultPort = 2000;
    public port: number = Number(process.env.PORT) || this.defaultPort;
    public appUrl: string = process.env.APP_URL || `http://localhost:${this.port}`;

    public time = new TimeConfig();
    public heroku = new HerokuConfig(this);
}
